package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.ListView;
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

public class RaidDisplay extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent a = new Intent(RaidDisplay.this,HomeFragment.class);
                    startActivity(a);
                    setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    Intent b = new Intent(RaidDisplay.this, PostFragment.class);
                    startActivity(b);
                    setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    Intent c = new Intent(RaidDisplay.this,NotificationsFragment.class);
                    startActivity(c);
                    setContentView(R.layout.fragment_notifications);
                    return true;
            }
            return false;
        }
    };


    String[] placeArray = {"Example"};

    String[] timeArray = {"Example"};

    String[] thingArray = {"Example"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_threads);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Context context = getApplicationContext();
        Thread thread = new Thread(new RankCollect(context));
        thread.start();

        try
        {
            RaidList raids = new RaidList(this, placeArray, timeArray, thingArray);
            ListView raidList = (ListView) findViewById(R.id.raidBoard);
            raidList.setAdapter(raids);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }



}
/*
class RaidCollect implements Runnable {
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

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            raidInformation.put("Command", "getRaids");

            os.write((raidInformation.toString()).getBytes());

            int x = inputStream.read(buffer);

            if(x >= 0)
            {
                String s = new String(buffer, 0, x);
                JSONArray json = new JSONArray();
                JSONObject j = json.getJSONObject(0);
                //String str = (String) json.get("status");
                //if(str.equals("OK")) {

                //Could be changed but JSON array is made for you
                HomeFragment.location [i] = (String) j.getString("location");
                HomeFragment.time = (String) j.getString("time");
                HomeFragment.pokemon = (String) j.getString("pokemon");

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
}
*/