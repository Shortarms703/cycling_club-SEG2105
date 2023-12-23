package com.example.cyclingclub;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cyclingclub.events.Event;

import java.util.List;

public class EventList extends ArrayAdapter<Event> {
    private Activity context;
    private List<Event> events;

    public EventList(Activity context, List<Event> events) {
        super(context, R.layout.layout_admin_event_list, events);
        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_admin_event_list, null, true);

        TextView eventName= (TextView) listViewItem.findViewById(R.id.eventName);
        TextView terrain = (TextView) listViewItem.findViewById(R.id.terrain);
        TextView eventLength = (TextView) listViewItem.findViewById(R.id.eventLength);
        TextView ageRequirement = (TextView) listViewItem.findViewById(R.id.ageRequirement);
        TextView eventFocus = (TextView) listViewItem.findViewById(R.id.eventFocus);
        TextView date  = (TextView) listViewItem.findViewById(R.id.date);
        TextView description = (TextView) listViewItem.findViewById(R.id.description);
        TextView distance = (TextView) listViewItem.findViewById(R.id.distance);

        Event event = events.get(position);

       eventName.setText(event.getEventName());
       terrain.setText(event.getTerrain());
       eventLength.setText(event.getEventLength());
       ageRequirement.setText(event.getEventAge());
       eventFocus.setText(event.getEventFocus());
       date.setText(event.getEventDate());
       description.setText(event.getDescription());
       distance.setText(event.getDistance());

        return listViewItem;
    }
}
