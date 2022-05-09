package com.example.sct15.pokemonraid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class NotificationsFragment extends AppCompatActivity {


    private TextView mTextMessage;
    static String username;
    static Integer faction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   // sendToast("Threads");
                    Intent a = new Intent(NotificationsFragment.this,HomeFragment.class);
                    startActivity(a);
                    setContentView(R.layout.fragment_threads);
                    return true;
                case R.id.navigation_dashboard:
                    sendToast("Post Thread");
                    Intent b = new Intent(NotificationsFragment.this, PostFragment.class);
                    startActivity(b);
                    setContentView(R.layout.fragment_postthread);
                    return true;
                case R.id.navigation_notifications:
                    sendToast("Trainer Card");
                    Intent c = new Intent(NotificationsFragment.this,NotificationsFragment.class);
                    startActivity(c);
                    setContentView(R.layout.fragment_notifications);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notifications);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().getItem(2).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        checkFaction();
        displayUser();

    }

    public void displayUser()
    {
        TextView text = (TextView) findViewById(R.id.NameTitle);
        text.setText(username);
    }

    public void checkFaction()
    {
        ImageView im = (ImageView) findViewById(R.id.FactionImg);
        if(faction == 1)
        {
            faction = R.drawable.instinct;
        }
        if(faction == 2)
        {
            faction = R.drawable.mystic;
        }
        if(faction == 3)
        {
            faction = R.drawable.valor;
        }

        im.setImageResource(faction);
    }

    public void checkRanks(View view)
    {
        Context context = getApplicationContext();
        Thread thread = new Thread(new RankCollect(context));
        thread.start();
        Intent a = new Intent(NotificationsFragment.this,RankSystem.class);
        startActivity(a);
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
/*
class getTrainer implements Runnable {
    Context cont;
    private final String IP = "codecrafters.co.nz";
    private final int PORT = 33713;
    Socket socket;
    OutputStream os;
    NotificationsFragment notif;

    @Override
    public void run() {

        try {
            InetAddress address = InetAddress.getByName(IP);
            socket = new Socket(address, PORT);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isR = new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(isR);
            os = socket.getOutputStream();

            //String read = notif.location.getText().toString();

            JSONObject raidInformation = new JSONObject();

            //Stores the JSON information to send to the server
            //raidInformation.put("Command", "get trainer " + read);

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

    public getTrainer(Context ctx, NotificationsFragment not) {
        cont = ctx;
        notif = not;
    }

    public getTrainer() { }

}
*/
