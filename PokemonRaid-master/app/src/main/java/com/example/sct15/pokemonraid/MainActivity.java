package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {


    private TextView mTextMessage;
/*
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Intent a = new Intent(MainActivity.this,HomeFragment.class);
                    startActivity(a);
                    setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent b = new Intent(MainActivity.this, PostFragment.class);
                    startActivity(b);
                    setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    // mTextMessage.setText(R.string.title_notifications);
                    Intent c = new Intent(MainActivity.this,NotificationsFragment.class);
                    startActivity(c);
                    setContentView(R.layout.fragment_notifications);
                    return true;
            }
            return false;
        }
    };

*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
    }

    /*
        All you need to know is call this method and the message inside to get a toast message.
    */
    public void sendToast(String msg)
    {
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

}

class HomeThread implements Runnable {
    private final String IP = "codecrafters.co.nz";
    private final int PORT = 33713;
    Socket socket;
    //MainActivity main = new MainActivity();
    Context context;
    OutputStream os;

    @Override
    public void run() {
        try {
            //main.sendToast("HI");
            InetAddress address = InetAddress.getByName(IP);
            socket = new Socket(address, PORT);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isR = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isR);
            os = socket.getOutputStream();
            byte [] buffer = new byte[1024];

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            raidInformation.put("Command", "raids");

            os.write((raidInformation.toString()).getBytes());

            int x = inputStream.read(buffer);

            if(x >= 0)
            {
                String s = new String(buffer, 0, x);
                JSONArray json = new JSONArray(s);

                //Prevent ArrayOutOfBounds Exception
                HomeFragment.location = new String[json.length()];
                HomeFragment.time = new String[json.length()];
                HomeFragment.pokemon = new String[json.length()];

                for(int i = 0; i < json.length(); i++) {

                    JSONObject j = json.getJSONObject(i);

                    //Could be changed but JSON array is made for you
                    HomeFragment.location [i] = (String) j.get("Location");
                    HomeFragment.time [i] = (String) j.get("Time");
                    HomeFragment.pokemon [i] = (String) j.get("Pokemon");
                }
                //String str = (String) json.get("status");
                //if(str.equals("OK")) {


                //}
                //For leaderboards instead of JSON Object call Json Array
            }
            os.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public HomeThread(Context ctx) {
        context = ctx;
    }

    public HomeThread() {

    }
}

class PostThread implements Runnable {

    PostFragment postThread;
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

            String[] read = new String[3];
            //Get the values for textviews and turning them into Strings
            read[0] = postThread.location.getText().toString();
            read[1] = postThread.time.getText().toString();
            read[2] = postThread.pokemon.getText().toString();

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            raidInformation.put("Command", "postRaid");
            raidInformation.put("Location", read[0]);
            raidInformation.put("Time", read[1]);
            raidInformation.put("Pokemon", read[2]);

            os.write((raidInformation.toString()).getBytes());
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

    public PostThread(Context ctx, PostFragment frag) {
        cont = ctx;
        postThread = frag;
    }

    public PostThread() { }
}