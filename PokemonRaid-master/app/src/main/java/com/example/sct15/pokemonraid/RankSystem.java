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

public class RankSystem extends AppCompatActivity {
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent a = new Intent(RankSystem.this,HomeFragment.class);
                    startActivity(a);
                    setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    Intent b = new Intent(RankSystem.this, PostFragment.class);
                    startActivity(b);
                    setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    Intent c = new Intent(RankSystem.this,NotificationsFragment.class);
                    startActivity(c);
                    setContentView(R.layout.fragment_notifications);
                    return true;
            }
            return false;
        }
    };


    static String[] trainerArray;

    static Integer[] numberArray;

    static String[] rankArray;

    static Integer[] factionArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank_system);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        for(int i = 0; i < factionArray.length; i++)
        {
            factionArray[i] = getDrawable(factionArray[i]);
            String s = getRank(numberArray[i]);
            rankArray[i] = s;
        }

        try
        {
            RankList ranks = new RankList(this, trainerArray, rankArray, factionArray);
            ListView rankList = (ListView) findViewById(R.id.rankBoard);
            rankList.setAdapter(ranks);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public String getRank(Integer num)
    {
        if(num == 1)
            return "Bug Catcher";
        if(num == 2)
            return "Trainer";
        if(num == 3)
            return "Cool Trainer";
        if(num == 4)
            return "Blackbelt";
        if(num == 5)
            return "Elite Four";
        return "";
    }

    public Integer getDrawable(Integer n)
    {
        if(n == 1)
        {
            return R.drawable.instinct;
        }
        if(n == 2)
        {
            return R.drawable.mystic;
        }
        if(n == 3)
        {
            return R.drawable.valor;
        }
        //This means error
        return R.drawable.emojimon;
    }

}

class RankCollect implements Runnable {
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
            raidInformation.put("Command", "getTrainers");

            os.write((raidInformation.toString()).getBytes());

            int x = inputStream.read(buffer);

            if(x >= 0)
            {
                String s = new String(buffer, 0, x);
                JSONArray json = new JSONArray(s);

                //Prevent ArrayOutOfBounds Exception
                RankSystem.trainerArray = new String[json.length()];
                RankSystem.numberArray = new Integer[json.length()];
                RankSystem.factionArray = new Integer[json.length()];
                RankSystem.rankArray = new String[json.length()];

                for(int i = 0; i < json.length(); i++) {

                    JSONObject j = json.getJSONObject(i);
                    //String str = (String) json.get("status");
                    //if(str.equals("OK")) {

                    //Could be changed but JSON array is made for you
                    RankSystem.trainerArray[i] = (String) j.get("Username");
                    RankSystem.factionArray[i] = Integer.parseInt(String.valueOf(j.get("Faction")));
                    RankSystem.numberArray[i] = Integer.parseInt(String.valueOf(j.get("Rank")));
                }

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

    public RankCollect(Context ctx) {
        cont = ctx;
    }

    public RankCollect() { }

}