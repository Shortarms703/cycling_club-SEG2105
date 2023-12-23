package com.example.cyclingclub;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclingclub.database.ClubEventTable;
import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.database.EventTable;
import com.example.cyclingclub.events.ClubEvent;
import com.example.cyclingclub.events.Event;

import java.util.ArrayList;
import java.util.List;

public class ClubEventListActivity extends AppCompatActivity {

    public List<Event> events;
    ListView listViewEvents;
    EventList eventAdapter;

    public List<ClubEvent> clubEvents;
    ListView listViewClubEvents;
    ClubEventList clubEventAdapter;

    int currentClub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_event_list);

        events = new ArrayList<>();
        clubEvents = new ArrayList<>();

        Bundle data = getIntent().getExtras();
        currentClub = data.getInt("accountID");
    }

    protected void onStart() {
        super.onStart();

        listViewEvents = (ListView) findViewById(R.id.eventTypeListView);
        listViewClubEvents = (ListView) findViewById(R.id.clubEventListView);

        DatabaseHelper dbHelper = new DatabaseHelper(ClubEventListActivity.this);

        events = EventTable.getEventsFromDatabase(dbHelper);
        clubEvents = ClubEventTable.getEventsGivenClubFromDatabase(currentClub, dbHelper);

        // creating adapter
        if(events != null){
            eventAdapter = new EventList(ClubEventListActivity.this, events);
            // attaching adapter to the listview
            listViewEvents.setAdapter(eventAdapter);
        }

        if (clubEvents != null) {
            clubEventAdapter = new ClubEventList(ClubEventListActivity.this, clubEvents, events);
            // attaching adapter to the listview
            listViewClubEvents.setAdapter(clubEventAdapter);
        }

        dbHelper.close();

        // making the listview clickable
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Event event = events.get(position);
                showAddEventDialog(event, position);
            }
        });

        listViewClubEvents.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                ClubEvent clubevent = clubEvents.get(position);
                showUpdateDelete(clubevent, position);
            }
        });
    }

    public void showAddEventDialog(Event event, int position) {
        Intent intent = new Intent(getApplicationContext(), AddClubEventActivity.class);

        intent.putExtra("eventID", event.getEventID());
        intent.putExtra("position", position);

        intent.putExtra("orgName", event.getEventName());
        intent.putExtra("orgTerrain", event.getTerrain());
        intent.putExtra("orgLength", event.getEventLength());
        intent.putExtra("orgAge", event.getEventAge());
        intent.putExtra("orgFocus", event.getEventFocus());
        intent.putExtra("orgDate", event.getEventDate());
        intent.putExtra("orgDesc", event.getDescription());
        intent.putExtra("orgDistance", event.getDistance());

        startActivityForResult(intent, 0);
    }

    public void showUpdateDelete(ClubEvent event, int position){
        Intent intent= new Intent(getApplicationContext(), Club_UpdateDeleteEventActivity.class);

        intent.putExtra("eventName", event.getEventTypeName());
        intent.putExtra("eventID", event.getClubEventID());
        intent.putExtra("eventTypeID", event.getEventType());
        intent.putExtra("position", position);
        intent.putExtra("orgDiff", event.getDifficulty());
        intent.putExtra("orgRoute", event.getRoute());
        intent.putExtra("orgFee", event.getFee());
        intent.putExtra("orgMaxParticipants", event.getMaxNumParticipants());
        intent.putExtra("numParticipants", event.getNumParticipants());
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) return;

        if (requestCode == 0) {
            // Returned from adding event
            Bundle extras = data.getExtras();

            int eventID = extras.getInt("eventID");
            int position = extras.getInt("position");

            String diff = extras.getString("diff");
            String route = extras.getString("route");
            Double fee = extras.getDouble("fee");
            int maxParticipants = extras.getInt("maxParticipants");

            ClubEvent newEvent = new ClubEvent(eventID, currentClub, diff, route, fee, maxParticipants, 0);

            DatabaseHelper dbHelper = new DatabaseHelper(ClubEventListActivity.this);
            newEvent = ClubEventTable.addClubEvent(newEvent, dbHelper);
            dbHelper.close();

            clubEvents.add(newEvent);
            clubEventAdapter.notifyDataSetChanged();
        }

        else if(requestCode ==1 ){
            //update
            if(resultCode==RESULT_OK){
                Bundle extras = data.getExtras();
                String action = extras.getString("action");
                int position = (int) extras.get("position");
                if(action.equals("update")){

                    int eventID = extras.getInt("eventID");
                    int eventTypeID = extras.getInt("eventTypeID");
                    int numParticipants = extras.getInt("numParticipants");
                    String eventName = extras.getString("eventName");

                    String orgDiff = extras.getString("orgDiff");
                    String orgRoute = extras.getString("orgRoute");
                    String ogFee = extras.getString("orgFee");
                    String ogMax = extras.getString("orgMaxParticipants");


                    double orgFee = Double.parseDouble(ogFee);
                    int orgMaxParticipants = Integer.parseInt(ogMax);

                    ClubEvent updatedEvent = new ClubEvent(eventTypeID, currentClub, orgDiff, orgRoute, orgFee, orgMaxParticipants, numParticipants);

                    //set the actual event id and eventName
                    updatedEvent.setClubEventID(eventID);
                    updatedEvent.setEventTypeName(eventName);

                    DatabaseHelper dbHelper = new DatabaseHelper(ClubEventListActivity.this);
                    ClubEventTable.updateClubEvent(updatedEvent, dbHelper);
                    dbHelper.close();

                    clubEvents.remove(position);
                    clubEvents.add(updatedEvent);
                    clubEventAdapter.notifyDataSetChanged();

                }
                else{
                    int eventID = extras.getInt("eventID");

                    clubEvents.remove(position);
                    clubEventAdapter.notifyDataSetChanged();

                    DatabaseHelper dbHelper = new DatabaseHelper(ClubEventListActivity.this);
                    ClubEventTable.deleteClubEvent(eventID, dbHelper);
                    dbHelper.close();
                }
            }

        }

    }

}
