package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddEventActivity extends AppCompatActivity {
    private EditText eventTypeNameEditText;
    private EditText terrainEditText;
    private EditText eventLengthEditText;
    private EditText ageRequirementEditText;
    private EditText eventFocusEditText;
    private EditText dateEditText;
    private EditText descriptionEditText;
    private EditText distanceEditText;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_type_dialog);
        eventTypeNameEditText = findViewById(R.id.addEventTypeName);
        terrainEditText = findViewById(R.id.addTerrain);
        eventLengthEditText = findViewById(R.id.addEventLength);
        ageRequirementEditText = findViewById(R.id.addAge);
        eventFocusEditText = findViewById(R.id.addEventFocus);
        dateEditText = findViewById(R.id.addDate);
        descriptionEditText = findViewById(R.id.addDescription);
        distanceEditText = findViewById(R.id.addDistance);


        Button addEventTypeButton = findViewById(R.id.addEventTypeBtn);
        addEventTypeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the input values from the EditText fields
                String eventTypeName = eventTypeNameEditText.getText().toString();
                String terrain = terrainEditText.getText().toString();
                String eventLength = eventLengthEditText.getText().toString();
                String ageRequirement = ageRequirementEditText.getText().toString();
                String eventFocus = eventFocusEditText.getText().toString();
                String date = dateEditText.getText().toString();
                String description = descriptionEditText.getText().toString();
                String distance = distanceEditText.getText().toString();

                boolean error = false;

                if (eventTypeName.trim().isEmpty()) {
                    eventTypeNameEditText.setError("Event type name cannot be empty");
                    error = true;
                }

                if (terrain.trim().isEmpty()) {
                    terrainEditText.setError("Terrain name cannot be empty");
                    error = true;
                }

                if (eventLength.trim().isEmpty()) {
                    eventLengthEditText.setError("Event length cannot be empty");
                    error = true;
                }
                else{
                    int length;
                    try {
                        length = Integer.parseInt(eventLength.trim());
                        if (length<0){
                            eventLengthEditText.setError("Length cannot be negative");
                            error=true;
                        }
                    }
                    catch(NumberFormatException e){
                        eventLengthEditText.setError("Event length must be a valid integer");
                        error = true;
                    }
                }


                if (ageRequirement.trim().isEmpty()) {
                    ageRequirementEditText.setError("Age requirement cannot be empty");
                    error = true;
                }
                else{
                    int age;
                    try {
                        age = Integer.parseInt(ageRequirement.trim());
                        if (age>100){
                            ageRequirementEditText.setError("Age requirement cannot be over a 100 years");
                            error=true;
                        }
                        if (age<0){
                            ageRequirementEditText.setError("Age requirement cannot be negative");
                            error=true;
                        }
                    }
                    catch(NumberFormatException e){
                        ageRequirementEditText.setError("Age requirement must be a valid integer");
                        error = true;
                    }
                }

                if (eventFocus.trim().isEmpty()) {
                    eventFocusEditText.setError("Event focus cannot be empty");
                    error = true;
                }

                if (date.trim().isEmpty()) {
                    dateEditText.setError("Date cannot be empty");
                    error = true;
                }
                if(date.trim().length()!=8){
                    dateEditText.setError("Date must be in valid format");
                    error = true;
                }
                else{
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.US);

                    try {
                        Date dateTest = sdf.parse(date);
                    } catch (ParseException e) {
                        dateEditText.setError("Date must be in valid format");
                        error = true;
                    }

                }

                if (description.trim().isEmpty()) {
                    descriptionEditText.setError("Description cannot be empty");
                    error = true;
                }

                if (distance.trim().isEmpty()) {
                    distanceEditText.setError("Distance cannot be empty");
                    error = true;
                }
                else{
                    int distanceTest;
                    try {
                        distanceTest = Integer.parseInt(distance.trim());
                        if (distanceTest<0){
                            distanceEditText.setError("Distance cannot be negative");
                            error=true;
                        }
                    }
                    catch(NumberFormatException e){
                        distanceEditText.setError("Distance must be a valid integer");
                        error = true;
                    }
                }

                if(error == false){
                    // Creating an Intent to return the input values
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("eventTypeName", eventTypeName);
                    resultIntent.putExtra("terrain", terrain);
                    resultIntent.putExtra("eventLength", eventLength);
                    resultIntent.putExtra("ageRequirement", ageRequirement);
                    resultIntent.putExtra("eventFocus", eventFocus);
                    resultIntent.putExtra("date", date);
                    resultIntent.putExtra("description", description);
                    resultIntent.putExtra("distance", distance);

                    setResult(RESULT_OK, resultIntent);
                    finish();
                }
            }
        });
    }

}
