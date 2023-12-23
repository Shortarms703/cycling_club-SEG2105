package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.accounts.Role;
import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.database.ParticipantTable;

import android.widget.DatePicker;

import java.util.Calendar;

import android.app.DatePickerDialog;

import java.util.regex.Pattern;

public class ParticipantSignUpActivity extends AppCompatActivity {

    private EditText birthDateEditText;
    private Button pickDateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_sign_up);

        EditText usernameField = findViewById(R.id.textView15);
        EditText passwordField = findViewById(R.id.textView21);
        EditText firstNameField = findViewById(R.id.textView11);
        EditText lastNameField = findViewById(R.id.textView13);
        EditText emailField = findViewById(R.id.textView17);
        EditText dateField = findViewById(R.id.editTextDate);
        EditText levelField = findViewById(R.id.textView19);

        Button signUp = findViewById(R.id.button6);

        birthDateEditText = findViewById(R.id.editTextDate);
        pickDateButton = findViewById(R.id.pickDateButton);

        pickDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameField.getText().toString();
                String lastName = lastNameField.getText().toString();
                String username = usernameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String date = dateField.getText().toString();
                String level = levelField.getText().toString();

                ParticipantAccount newParticipant = new ParticipantAccount(username, password, email, firstName, lastName, Role.participant, date, level);

                boolean error = false;
                if (firstName.trim().isEmpty()) {
                    firstNameField.setError("This field cannot be empty");
                    error = true;
                }
                if (lastName.trim().isEmpty()) {
                    lastNameField.setError("This field cannot be empty");
                    error = true;
                }
                if (username.trim().isEmpty()) {
                    usernameField.setError("This field cannot be empty");
                    error = true;
                }
                if (email.trim().isEmpty()) {
                    emailField.setError("This field cannot be empty");
                    error = true;
                }
                if (password.trim().isEmpty()) {
                    passwordField.setError("This field cannot be empty");
                    error = true;
                }
                if (TextUtils.isEmpty(date)) {
                    dateField.setError("This field cannot be empty");
                    error = true;
                }
                if (level.trim().isEmpty()) {
                    levelField.setError("This field cannot be empty");
                    error = true;
                }
                if (!newParticipant.validateUniqueUsername(new DatabaseHelper(ParticipantSignUpActivity.this))) {
                    usernameField.setError("This username is already taken");
                    error = true;
                }
                if (!newParticipant.validatePassword()) {
                    passwordField.setError("This is not a valid password, should be at least 8 characters long");
                    error = true;
                }
                if (!newParticipant.validateEmail()) {
                    emailField.setError("This is not a valid email address, should be like example@google.com");
                    error = true;
                }
                if (error == false) {
                    DatabaseHelper dbHelper = new DatabaseHelper(ParticipantSignUpActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    ParticipantTable.addAccount(newParticipant, db);

                    db.close();
                    dbHelper.close();
                    finish();
                }

            }
        });
    }

    public void showDatePickerDialog(View view) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        birthDateEditText.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }

}