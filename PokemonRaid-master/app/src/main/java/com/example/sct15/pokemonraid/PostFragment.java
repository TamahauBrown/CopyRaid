package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sct15.pokemonraid.HomeFragment;
import com.example.sct15.pokemonraid.LoginScreen;
import com.example.sct15.pokemonraid.NotificationsFragment;
import com.example.sct15.pokemonraid.R;

public class PostFragment extends AppCompatActivity {

    private TextView mTextMessage;
    TextView location;
    TextView time;
    TextView pokemon;
    static boolean loggedIn = false;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //sendToast("Threads");
                    Intent a = new Intent(PostFragment.this, HomeFragment.class);
                    startActivity(a);
                    //setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    sendToast("Post Thread");
                    Intent b = new Intent(PostFragment.this, PostFragment.class);
                    startActivity(b);
                    //setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    sendToast("Trainer Card");
                    Intent c = new Intent(PostFragment.this, NotificationsFragment.class);
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
        setContentView(R.layout.fragment_postthread);

        if(!loggedIn)
        {
            Intent a = new Intent(this, LoginScreen.class);
            startActivity(a);
        }

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(1).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        location = (TextView) findViewById(R.id.getUsername);
        time = (TextView) findViewById(R.id.editTextWhen);
        pokemon = (TextView) findViewById(R.id.editTextWhat);

        //This line is causing the error to occur
        Context context = getApplicationContext();
    }

    public void postRaid(View view)
    {
        Context cont = getApplicationContext();
        Thread thread = new Thread(new PostThread(cont, this));
        thread.start();
        sendToast("Message sent");
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
