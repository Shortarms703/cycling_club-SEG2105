package com.example.cyclingclub;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.database.ParticipantEventTable;

import java.util.ArrayList;
import java.util.List;

public class ParticipantEventActivity extends AppCompatActivity {
    public ListView p_events;
    public List<String> events;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.participant_event_activity);
        events = new ArrayList<>();
    }

    @Override
    protected void onStart(){
        super.onStart();

        p_events = (ListView) findViewById(R.id.p_events);

        DatabaseHelper dbHelper = new DatabaseHelper(ParticipantEventActivity.this);
        events = ParticipantEventTable.getpEvents(dbHelper);

        ArrayAdapter<String> adapter = new ParticipantEventList(ParticipantEventActivity.this, events);
        p_events.setAdapter(adapter);

        dbHelper.close();
    }

}
