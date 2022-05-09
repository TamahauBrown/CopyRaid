package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class LoginScreen extends AppCompatActivity {
    TextView username;
    TextView password;
    static String loginFail;
    static boolean isTrue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    public void CreateAcc(View view)
    {
        Intent b = new Intent(this, CreateAccount.class);
        startActivity(b);
    }

    public void Login(View view) {
        Context cont = getApplicationContext();

        //Gets the text field values of the username and password
        username = (TextView) findViewById(R.id.getUsername);
        password = (TextView) findViewById(R.id.getPassword);



        if (!username.getText().toString().equals("") && username.getText().toString().length() <= 8)
        {
            if(!password.getText().toString().equals("") && password.getText().toString().length() <= 6)
            {
                Context context = getApplicationContext();
                Thread thread = new Thread(new LogIn(context, this));
                thread.start();

                if(isTrue)
                {
                    PostFragment.loggedIn = true;
                    HomeFragment.isStartup = false;
                    Intent b = new Intent(this, PostFragment.class);
                    startActivity(b);
                }
                else
                {
                    TextView message = (TextView) findViewById(R.id.LoginMessage);
                    message.setText("Login error: " + loginFail + " Please try again");
                }
            }
        }
    }
}


class LogIn implements Runnable {

    LoginScreen login;
    Context cont;
    private final String IP = "codecrafters.co.nz";
    private final int PORT = 33713;
    Socket socket;
    OutputStream os;

    @Override
    public void run() {

        try {
            InetAddress address = InetAddress.getByName(IP);
            socket = new Socket(address, PORT);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isR = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isR);
            byte[] buffer = new byte[1024];
            os = socket.getOutputStream();

            String[] read = new String[2];
            //Get the values for textviews and turning them into Strings
            read[0] = login.username.getText().toString();
            read[1] = login.password.getText().toString();

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            raidInformation.put("Command", "login");
            raidInformation.put("Username", read[0]);
            raidInformation.put("Password", read[1]);

            os.write((raidInformation.toString()).getBytes());

            int x = inputStream.read(buffer);

            if (x >= 0) {
                String str = "";
                String s = new String(buffer, 0, x);
                JSONObject json = new JSONObject(s);
                if(json.has("Status")) {
                    str = json.getString("Status");
                }
                if (str.equals("OK")) {
                    NotificationsFragment.username = (String) json.get("Username");
                    String ss = String.valueOf(json.get("Faction"));
                    NotificationsFragment.faction = Integer.parseInt(ss);
                    LoginScreen.isTrue = true;
                }
                else
                {
                    if(json.has("Message"))
                    {
                        LoginScreen.loginFail = (String) json.get("Message");
                        LoginScreen.isTrue = false;
                    }
                }
                //For leaderboards instead of JSON Object call Json Array
                }

                os.close();
            }
        catch (UnknownHostException exc) {
            exc.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public LogIn(Context ctx, LoginScreen frag) {
        cont = ctx;
        login = frag;
    }

    public LogIn() { }
}
