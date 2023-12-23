package com.example.cyclingclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Club_UpdateDeleteEventActivity extends AppCompatActivity {
    private EditText etextDifficulty;
    private EditText etextRoute;
    private EditText etextFee;
    private EditText etextMaxNumParticipants;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.club_update_delete);

        etextDifficulty=findViewById(R.id.etextDifficulty);
        etextRoute = findViewById(R.id.etextRoute);
        etextFee= findViewById(R.id.etextFee);
        etextMaxNumParticipants=findViewById(R.id.etextMaxNumParticipants);

        Bundle data = getIntent().getExtras();

        int position = data.getInt("position");

        String eventName = data.getString("eventName");

        int eventID = data.getInt("eventID");
        int eventTypeID = data.getInt("eventTypeID");
        String orgDiff = data.getString("orgDiff");
        String orgRoute = data.getString("orgRoute");
        Double orgFee = data.getDouble("orgFee");
        int orgMaxNumParticipants = data.getInt("orgMaxParticipants");
        int numParticipants = data.getInt("numParticipants");

        etextDifficulty.setText(orgDiff, TextView.BufferType.EDITABLE);
        etextRoute.setText(orgRoute, TextView.BufferType.EDITABLE);
        etextFee.setText(String.valueOf(orgFee), TextView.BufferType.EDITABLE);
        etextMaxNumParticipants.setText(String.valueOf(orgMaxNumParticipants), TextView.BufferType.EDITABLE);


        Button update = findViewById(R.id.c_btnUpdate);
        Button delete = findViewById(R.id.c_btnDelete);

        delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "delete");
                resultIntent.putExtra("position", position);
                resultIntent.putExtra("eventID", eventID);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("action", "update");
                resultIntent.putExtra("eventName", eventName);
                resultIntent.putExtra("position", position);
                resultIntent.putExtra("eventID", eventID);
                resultIntent.putExtra("eventTypeID", eventTypeID);
                resultIntent.putExtra("orgDiff", etextDifficulty.getText().toString());
                resultIntent.putExtra("orgRoute", etextRoute.getText().toString());
                resultIntent.putExtra("orgFee", etextFee.getText().toString());
                resultIntent.putExtra("orgMaxParticipants", etextMaxNumParticipants.getText().toString());
                resultIntent.putExtra("numParticipants", numParticipants);

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
