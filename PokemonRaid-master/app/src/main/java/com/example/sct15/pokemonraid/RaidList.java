package com.example.sct15.pokemonraid;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RaidList extends ArrayAdapter {

    private final Activity context;
    private final String[] where;
    private final String[] when;
    private final String[] what;

    public RaidList(Activity context, String[] time, String[] place, String[] name)
    {
        super(context, R.layout.raidlist, place);

        this.context = context;
        this.what = name;
        this.when = time;
        this.where = place;
    }

    @Override
    public View getView(int pos, View view, ViewGroup par)
    {
        LayoutInflater inflates = context.getLayoutInflater();
        View row = inflates.inflate(R.layout.raidlist, null, true);

        //Gets the input stuff
        TextView where = (TextView) row.findViewById(R.id.raidPlace);
        TextView when = (TextView) row.findViewById(R.id.raidTime);
        TextView what = (TextView) row.findViewById(R.id.raidName);

        //Allows you to add the time, place and name of the raid
        where.setText(this.where[pos]);
        when.setText(this.when[pos]);
        what.setText(this.what[pos]);

        return row;
    }
}
