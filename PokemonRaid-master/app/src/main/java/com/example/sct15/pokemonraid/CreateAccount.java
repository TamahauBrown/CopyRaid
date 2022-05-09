package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class CreateAccount extends AppCompatActivity {

    TextView username;
    TextView password;
    int faction = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
    }

    public void Mystic(View view)
    {
        faction = 2;
    }

    public void Valor(View view)
    {
        faction = 3;
    }

    public void Instinct(View view)
    {
        faction = 1;
    }

    public void Create(View view)
    {
        Context cont = getApplicationContext();

        //Gets the text field values of the username and password
        username = (TextView) findViewById(R.id.getUsername);
        password = (TextView) findViewById(R.id.getPassword);

        if (!username.getText().toString().equals("") && username.getText().toString().length() <= 8)
        {
            if(!password.getText().toString().equals("") && password.getText().toString().length() <= 6 && faction != 0)
            {
                Context context = getApplicationContext();
                Thread thread = new Thread(new Create(context, this));
                thread.start();

                PostFragment.loggedIn = true;
                HomeFragment.isStartup = false;
                Intent b = new Intent(this, PostFragment.class);
                startActivity(b);
            }
        }
    }
}



class Create implements Runnable {

    CreateAccount login;
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
            os = socket.getOutputStream();
            byte [] buffer = new byte[1024];

            String[] read = new String[2];

            //Get the values for textviews and turning them into Strings
            read[0] = login.username.getText().toString();
            read[1] = login.password.getText().toString();

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            raidInformation.put("Command", "create");
            raidInformation.put("Username", read[0]);
            raidInformation.put("Password", read[1]);
            raidInformation.put("Faction", login.faction);

            os.write((raidInformation.toString()).getBytes());

            int x = inputStream.read(buffer);

            if(x >= 0)
            {
                String s = new String(buffer, 0, x);
                JSONObject json = new JSONObject();
                //String str = (String) json.get("status");
                //if(str.equals("OK")) {
                NotificationsFragment.username = (String) json.get("Username");
                String ss = String.valueOf(json.get("Faction"));
                //}
                //For leaderboards instead of JSON Object call Json Array
            }
            os.close();
        }
        catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public Create(Context ctx, CreateAccount frag) {
        cont = ctx;
        login = frag;
    }

    public Create() { }
}

