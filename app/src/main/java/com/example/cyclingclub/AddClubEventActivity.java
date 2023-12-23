package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class AddClubEventActivity extends AppCompatActivity {
    // Instance variables

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_club_event_dialog);

        // Get XML field attributes
        TextView eventName= (TextView) findViewById(R.id.eventName);
        TextView terrain = (TextView) findViewById(R.id.terrain);
        TextView eventLength = (TextView) findViewById(R.id.eventLength);
        TextView ageRequirement = (TextView) findViewById(R.id.ageRequirement);
        TextView eventFocus = (TextView) findViewById(R.id.eventFocus);
        TextView date  = (TextView) findViewById(R.id.date);
        TextView description = (TextView) findViewById(R.id.description);
        TextView distance = (TextView) findViewById(R.id.distance);

        // Bind XML input fields
        EditText etxtDiff = findViewById(R.id.etxtDiff);
        EditText etxtRoute = findViewById(R.id.etxtRoute);
        EditText etxtFee = findViewById(R.id.etxtFee);
        EditText etxtMaxParticipants = findViewById(R.id.etxtMaxParticipants);

        // Get data from intent
        Bundle data = getIntent().getExtras();

        int eventID = data.getInt("eventID");
        int position = data.getInt("position");

        String orgName = data.getString("orgName");
        String orgTerrain = data.getString("orgTerrain");
        String orgLength = data.getString("orgLength");
        String orgAge = data.getString("orgAge");
        String orgFocus = data.getString("orgFocus");
        String orgDate = data.getString("orgDate");
        String orgDesc = data.getString("orgDesc");
        String orgDistance = data.getString("orgDistance");

        // Display data
        eventName.setText(orgName, TextView.BufferType.EDITABLE);
        terrain.setText(orgTerrain, TextView.BufferType.EDITABLE);
        eventLength.setText(orgLength, TextView.BufferType.EDITABLE);
        ageRequirement.setText(orgAge, TextView.BufferType.EDITABLE);
        eventFocus.setText(orgFocus, TextView.BufferType.EDITABLE);
        date.setText(orgDate, TextView.BufferType.EDITABLE);
        description.setText(orgDesc, TextView.BufferType.EDITABLE);
        distance.setText(orgDistance, TextView.BufferType.EDITABLE);

        Button btnReturn = (Button) findViewById(R.id.btnReturn);
        btnReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();

                String diff = etxtDiff.getText().toString();
                String route = etxtRoute.getText().toString();
                String feeStr = etxtFee.getText().toString();
                String maxParticipantsStr = etxtMaxParticipants.getText().toString();
                boolean error = false;
                Double fee = null;
                int maxParticipants = 0;
                try {
                    // Attempt to parse the fee as a Double
                    fee = Double.parseDouble(feeStr);
                } catch (NumberFormatException e) {
                    // Handle the exception if not a valid fee
                    etxtFee.setError("This field needs to be a number");
                    error = true;
                }
                try {
                    // Attempt to parse the fee as an Integer
                    maxParticipants = Integer.valueOf(maxParticipantsStr);
                } catch (NumberFormatException e) {
                    // Handle the exception if not a valid number of participants
                    etxtMaxParticipants.setError("This field needs to be a number");
                    error = true;
                }
                if (diff.trim().isEmpty()) {
                    etxtDiff.setError("This field cannot be empty");
                    error = true;
                }
                if (route.trim().isEmpty()) {
                    etxtRoute.setError("This field cannot be empty");
                    error = true;
                }
                if (feeStr.isEmpty()) {
                    etxtFee.setError("This field cannot be empty");
                    error = true;
                }
                if(fee<0){
                    etxtFee.setError("Cannot have a negative fee");
                    error = true;
                }
                if (maxParticipantsStr.isEmpty()) {
                    etxtMaxParticipants.setError("This field cannot be empty");
                    error = true;
                }
                if(maxParticipants<=0){
                    etxtMaxParticipants.setError("An event needs to have participants");
                    error = true;
                }

                if(error==false){
                    resultIntent.putExtra("eventID", eventID);
                    resultIntent.putExtra("position", position);

                    resultIntent.putExtra("diff", diff);
                    resultIntent.putExtra("route", route);
                    resultIntent.putExtra("fee", fee);
                    resultIntent.putExtra("maxParticipants", maxParticipants);
                    setResult(RESULT_OK, resultIntent);
                    finish();
                }

            }
        });
    }
}
