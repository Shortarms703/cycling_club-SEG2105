package com.example.cyclingclub;

import android.app.Activity;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class ParticipantEventList extends ArrayAdapter<String> {
    private Activity context;
    private List<String> events;
    public ParticipantEventList(Activity context, List<String> events){
        super(context, R.layout.participant_event_list, events);
        this.context=context;
        this.events=events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.participant_event_list, null, true);

        TextView eventName = (TextView) listViewItem.findViewById(R.id.p_eventName);

        String event = events.get(position);

        eventName.setText(event);
        return listViewItem;
    }
}
