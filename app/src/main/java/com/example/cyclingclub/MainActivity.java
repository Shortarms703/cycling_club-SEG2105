package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cyclingclub.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void navigateToSignInActivity(View view) {
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    public void navigateToClubSignUpActivity(View view) {
        Intent intent = new Intent(this, ClubSignUpActivity.class);
        startActivity(intent);
    }

    public void navigateToParticipantSignUpActivity(View view) {
        Intent intent = new Intent(this, ParticipantSignUpActivity.class);
        startActivity(intent);
    }
}