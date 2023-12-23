package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cyclingclub.accounts.CyclingClubAccount;
import com.example.cyclingclub.accounts.Role;
import com.example.cyclingclub.database.CyclingClubTable;
import com.example.cyclingclub.database.DatabaseHelper;

public class ClubSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_sign_up);

        // Find the EditText widget by its ID
        EditText firstNameField = findViewById(R.id.textView11);
        EditText lastNameField = findViewById(R.id.textView13);
        EditText usernameField = findViewById(R.id.textView15);
        EditText emailField = findViewById(R.id.textView17);
        EditText passwordField = findViewById(R.id.textView21);
        EditText clubNameField = findViewById(R.id.textView8);
        EditText addressField = findViewById(R.id.textView23);
        EditText postalCodeField = findViewById(R.id.textView25);

        Button signUp = findViewById(R.id.button6);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = firstNameField.getText().toString();
                String lastName = lastNameField.getText().toString();
                String username = usernameField.getText().toString();
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();
                String clubName = clubNameField.getText().toString();
                String address = addressField.getText().toString();
                String postalCode = postalCodeField.getText().toString();

                CyclingClubAccount newClub = new CyclingClubAccount(username, password, email,
                        firstName, lastName, Role.club,
                        clubName, address, postalCode
                );

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
                if (clubName.trim().isEmpty()) {
                    clubNameField.setError("This field cannot be empty");
                    error = true;
                }
                if (address.trim().isEmpty()) {
                    addressField.setError("This field cannot be empty");
                    error = true;
                }
                if (postalCode.trim().isEmpty()) {
                    postalCodeField.setError("This field cannot be empty");
                    error = true;
                }
                if (!newClub.validateUniqueUsername(new DatabaseHelper(ClubSignUpActivity.this))) {
                    usernameField.setError("This username is already taken");
                    error = true;
                }
                if (!newClub.validatePassword()) {
                    passwordField.setError("This is not a valid password, should be at least 8 characters long");
                    error = true;
                }
                if (!newClub.validateEmail()) {
                    emailField.setError("This is not a valid email address, should be like example@google.com");
                    error = true;
                }
                if (!newClub.validatePostalCode()) {
                    postalCodeField.setError("This is not a valid postal code, should be like A#B#C#");
                    error = true;
                }
                if (error == false) {
                    DatabaseHelper dbHelper = new DatabaseHelper(ClubSignUpActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();

                    CyclingClubTable.addAccount(newClub, db);

                    dbHelper.close();
                    finish();
                }

            }
        });
    }
}
