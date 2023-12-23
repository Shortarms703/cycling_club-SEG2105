package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.database.EventTable;
import com.example.cyclingclub.events.Event;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.TextView;
import android.widget.AdapterView;

public class EventManagerActivity extends AppCompatActivity{

    public List<Event> events;
    public ListView listViewEvents;
    public EventList eventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_manager);
        events = new ArrayList<>();
    }

    protected void onStart() {
        super.onStart();

        listViewEvents = (ListView) findViewById(R.id.eventListView);

        DatabaseHelper dbHelper = new DatabaseHelper(EventManagerActivity.this);
        events = EventTable.getEventsFromDatabase(dbHelper);

        // creating adapter
        if(events != null){
            eventAdapter = new EventList(EventManagerActivity.this, events);
            // attaching adapter to the listview
            listViewEvents.setAdapter(eventAdapter);
        }
        dbHelper.close();

        // making the listView clickable
        listViewEvents.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Event event = events.get(position);
                showUpdateDeleteDialog(event, position);
            }
        });
    }


    public void showAddEventTypeDialog(View view) {
        //Application Context and Activity
        Intent intent = new Intent(getApplicationContext(), AddEventActivity.class);
        startActivityForResult(intent, 0);
    }

    public void showUpdateDeleteDialog(Event event, int position) {
        Intent intent = new Intent(getApplicationContext(), UpdateDeleteEventActivity.class);

        // Pass event to Intent
        intent.putExtra("position", position);
        intent.putExtra("orgName", event.getEventName());
        intent.putExtra("orgTerrain", event.getTerrain());
        intent.putExtra("orgLength", event.getEventLength());
        intent.putExtra("orgAge", event.getEventAge());
        intent.putExtra("orgFocus", event.getEventFocus());
        intent.putExtra("orgDate", event.getEventDate());
        intent.putExtra("orgDesc", event.getDescription());
        intent.putExtra("orgDistance", event.getDistance());
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CANCELED) return;

        //getting intent attributes returned from add event type dialog

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Check if the result is OK
                String eventTypeName = data.getStringExtra("eventTypeName");
                String terrain = data.getStringExtra("terrain");
                String eventLength = data.getStringExtra("eventLength");
                String ageRequirement = data.getStringExtra("ageRequirement");
                String eventFocus = data.getStringExtra("eventFocus");
                String date = data.getStringExtra("date");
                String description = data.getStringExtra("description");
                String distance = data.getStringExtra("distance");

                Event newEvent = new Event(eventTypeName, terrain, eventLength, ageRequirement,eventFocus, date, description, distance);

                DatabaseHelper dbHelper = new DatabaseHelper(EventManagerActivity.this);

                int newID = EventTable.addEvent(newEvent, dbHelper);
                newEvent.setEventID(newID);

                dbHelper.close();

                events.add(newEvent);
                eventAdapter.notifyDataSetChanged();
            }
        }
        // Update
        else if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                String action = extras.getString("action");
                int position = extras.getInt("position");
                if (action.equals("update")) {
                    String eventTypeName = data.getStringExtra("eventTypeName");
                    String terrain = data.getStringExtra("terrain");
                    String eventLength = data.getStringExtra("eventLength");
                    String ageRequirement = data.getStringExtra("ageRequirement");
                    String eventFocus = data.getStringExtra("eventFocus");
                    String date = data.getStringExtra("date");
                    String description = data.getStringExtra("description");
                    String distance = data.getStringExtra("distance");

                    Event newEvent= new Event(eventTypeName, terrain, eventLength,
                            ageRequirement,eventFocus, date, description, distance);

                    events.remove(position);
                    events.add(newEvent);
                    eventAdapter.notifyDataSetChanged();

                    DatabaseHelper dbHelper = new DatabaseHelper(EventManagerActivity.this);

                    EventTable.updateEvent(newEvent, dbHelper);
                    dbHelper.close();
                }
                else {
                    String eventName = extras.getString("eventTypeName");

                    events.remove(position);
                    eventAdapter.notifyDataSetChanged();

                    DatabaseHelper dbHelper = new DatabaseHelper(EventManagerActivity.this);

                    EventTable.deleteEvent(eventName, dbHelper);
                    dbHelper.close();
                }
            }
        }
    }

}
