package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends AppCompatActivity {

    private TextView mTextMessage;
    static boolean isStartup = true;
    static String [] location;
    static String [] time;
    static String [] pokemon;

    //String[] placeArray = {"Example"};

    //String[] timeArray = {"Example"};

    //String[] thingArray = {"Example"};

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    sendToast("Threads");
                    Intent a = new Intent(HomeFragment.this,HomeFragment.class);
                    startActivity(a);
                    setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    sendToast("Post Thread");
                    Intent b = new Intent(HomeFragment.this, PostFragment.class);
                    startActivity(b);
                    setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    sendToast("Trainer Card");
                    Intent c = new Intent(HomeFragment.this,NotificationsFragment.class);
                    startActivity(c);
                    //setContentView(R.layout.fragment_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_threads);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(0).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //This line is causing the error to occur
        Context context = getApplicationContext();


        if(!isStartup) {
            Thread thread = new Thread(new HomeThread(context));
            thread.start();
        }


        try
        {
            RaidList raids = new RaidList(this, location, time, pokemon);
            ListView raidList = (ListView) findViewById(R.id.raidBoard);
            raidList.setAdapter(raids);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }
/*
    @Override
    protected void onResume() {

    }
*/
    /*
               Displays threads
               where: Location of the raid
               when: When time the raid is occurring.
               what: What Pokemon is being hunted
        *//*
    public void ThreadDisplay(String where, String when, String what)
    {
        TextView textView = (TextView) findViewById(R.id.NameRank);
        textView.setText("Event Location: " + where +
                "\n Event Time: " + when +
                "\n Raid Pokemon: " + what);
    }*/

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
