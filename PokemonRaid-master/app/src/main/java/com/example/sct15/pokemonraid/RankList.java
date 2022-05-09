package com.example.sct15.pokemonraid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.security.PrivateKey;

public class RankList extends ArrayAdapter {
    private final Activity context;
    private final Integer[] playerImage;
    private final String[] playerName;
    private final String[] playerRank;

    public RankList(Activity context, String[] names, String[] ranks, Integer[] images)
    {
        super(context, R.layout.ranklist, names);

        this.context = context;
        this.playerImage = images;
        this.playerName = names;
        this.playerRank = ranks;
    }

    @Override
    public View getView(int pos, View view, ViewGroup par)
    {
        LayoutInflater inflates = context.getLayoutInflater();
        View row = inflates.inflate(R.layout.ranklist, null, true);

        //Gets the input stuff
        TextView name = (TextView) row.findViewById(R.id.playerName);
        TextView rank = (TextView) row.findViewById(R.id.playerRank);
        ImageView image = (ImageView) row.findViewById(R.id.playerImage);

        //Allows you to add the name and image of the user
        name.setText(playerName[pos]);
        rank.setText(playerRank[pos]);
        image.setImageResource(playerImage[pos]);

        return row;
    }
}
