package com.example.cyclingclub;

import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.events.ClubEvent;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import com.example.cyclingclub.events.Event;

public class ClubEventList extends ArrayAdapter<ClubEvent> {
    private Activity context;
    List<ClubEvent> events;
    List<Event> e;

    public ClubEventList(Activity context, List<ClubEvent> events, List<Event> e) {
        super(context, R.layout.club_event_list, events);
        this.context = context;
        this.events = events;
        this.e = e;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.club_event_list, null, true);

        TextView eventName = (TextView) listViewItem.findViewById(R.id.eventName);

        TextView diff = (TextView) listViewItem.findViewById(R.id.diff);
        TextView route = (TextView) listViewItem.findViewById(R.id.route);
        TextView fee = (TextView) listViewItem.findViewById(R.id.fee);
        TextView maxNumParticipants = (TextView) listViewItem.findViewById(R.id.maxNumParticipants);
        TextView numParticipants = (TextView) listViewItem.findViewById(R.id.numParticipants);

        ClubEvent clubEvent = events.get(position);

        eventName.setText(clubEvent.getEventTypeName());

        diff.setText(clubEvent.getDifficulty());
        route.setText(clubEvent.getRoute());
        fee.setText(Double.toString(clubEvent.getFee()));
        maxNumParticipants.setText(Integer.toString(clubEvent.getMaxNumParticipants()));
        numParticipants.setText(Integer.toString(clubEvent.getNumParticipants()));

        return listViewItem;
    }

}
