package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import com.example.cyclingclub.events.Event;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UpdateDeleteEventActivity extends AppCompatActivity {
    private EditText etxtName;
    private EditText etxtTerrain;
    private EditText etxtLength;
    private EditText etxtAge;
    private EditText etxtFocus;
    private EditText etxtDate;
    private EditText etxtDesc;
    private EditText etxtDistance;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_delete_dialog);

        // Bind to XML attributes
        etxtName = findViewById(R.id.etxtName);
        etxtTerrain = findViewById(R.id.etxtTerrain);
        etxtLength = findViewById(R.id.etxtLength);
        etxtAge = findViewById(R.id.etxtAge);
        etxtFocus = findViewById(R.id.etxtFocus);
        etxtDesc = findViewById(R.id.etxtDesc);
        etxtDate = findViewById(R.id.etxtDate);
        etxtDistance = findViewById(R.id.etxtDistance);

        // Get data from calling activity
        Bundle data = getIntent().getExtras();

        // Get original data to display in edit text fields
        int position = data.getInt("position");

        String orgName = data.getString("orgName");
        String orgTerrain = data.getString("orgTerrain");
        String orgLength = data.getString("orgLength");
        String orgAge = data.getString("orgAge");
        String orgFocus = data.getString("orgFocus");
        String orgDate = data.getString("orgDate");
        String orgDesc = data.getString("orgDesc");
        String orgDistance = data.getString("orgDistance");

        // Set text and allow edit
        etxtName.setText(orgName, TextView.BufferType.EDITABLE);
        etxtTerrain.setText(orgTerrain, TextView.BufferType.EDITABLE);
        etxtLength.setText(orgLength, TextView.BufferType.EDITABLE);
        etxtAge.setText(orgAge, TextView.BufferType.EDITABLE);
        etxtFocus.setText(orgFocus, TextView.BufferType.EDITABLE);
        etxtDate.setText(orgDate, TextView.BufferType.EDITABLE);
        etxtDesc.setText(orgDesc, TextView.BufferType.EDITABLE);
        etxtDistance.setText(orgDistance, TextView.BufferType.EDITABLE);

        Button update = findViewById(R.id.btnUpdate);
        Button delete = findViewById(R.id.btnDelete);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the input values from the EditText fields
                String eventTypeName = etxtName.getText().toString();
                String terrain = etxtTerrain.getText().toString();
                String eventLength = etxtLength.getText().toString();
                String ageRequirement = etxtAge.getText().toString();
                String eventFocus = etxtFocus.getText().toString();
                String date = etxtDate.getText().toString();
                String description = etxtDesc.getText().toString();
                String distance = etxtDistance.getText().toString();

                boolean error = false;

                if (eventTypeName.trim().isEmpty()) {
                    etxtName.setError("event type name cannot be empty");
                    error = true;
                }

                if (terrain.trim().isEmpty()) {
                    etxtTerrain.setError("terrain name cannot be empty");
                    error = true;
                }

                if (eventLength.trim().isEmpty()) {
                    etxtLength.setError("event length cannot be empty");
                    error = true;
                }
                else{
                    int length;
                    try {
                        length = Integer.parseInt(eventLength.trim());
                    }
                    catch(NumberFormatException e){
                        etxtLength.setError("event length must be a valid integer");
                        error = true;
                    }
                }

                if (ageRequirement.trim().isEmpty()) {
                    etxtAge.setError("age requirement cannot be empty");
                    error = true;
                }
                else{
                    int age;
                    try {
                        age = Integer.parseInt(ageRequirement.trim());
                    }
                    catch(NumberFormatException e){
                        etxtAge.setError("age requirement must be a valid integer");
                        error = true;
                    }
                }

                if (eventFocus.trim().isEmpty()) {
                    etxtFocus.setError("event focus cannot be empty");
                    error = true;
                }

                if (date.trim().isEmpty()) {
                    etxtDate.setError("date cannot be empty");
                    error = true;
                }
                else{
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.US);

                    try {
                        Date dateTest = sdf.parse(date);
                    } catch (ParseException e) {
                        etxtDate.setError("date must be in valid format");
                        error = true;
                    }

                }

                if (description.trim().isEmpty()) {
                    etxtDesc.setError("description cannot be empty");
                    error = true;
                }

                if (distance.trim().isEmpty()) {
                    etxtDistance.setError("distance cannot be empty");
                    error = true;
                }
                else{
                    int distanceTest;
                    try {
                        distanceTest = Integer.parseInt(distance.trim());
                    }
                    catch(NumberFormatException e){
                        etxtDistance.setError("distance must be a valid integer");
                        error = true;
                    }
                }

                // Create an Intent to return the input values

                if (error == false){
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("action", "update");
                    resultIntent.putExtra("position", position);

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

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "delete");
                resultIntent.putExtra("position", position);

                String eventTypeName = etxtName.getText().toString();
                resultIntent.putExtra("eventTypeName", eventTypeName);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
