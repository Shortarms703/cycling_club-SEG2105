package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cyclingclub.accounts.Account;

public class AdminProfileActivity extends AppCompatActivity {

    Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_profile);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");

        TextView firstName = findViewById(R.id.textView2);
        TextView role = findViewById(R.id.textView4);

        firstName.setText(firstName.getText().toString().replace("[firstname]", account.getFirstName()));
        role.setText(role.getText().toString().replace("[role]", account.getRole().toString()));

    }
    public void navigateToEventManager(View view) {
        if(account.getRole().toString().equals("admin")){
            Intent intent = new Intent(this, EventManagerActivity.class);
            startActivity(intent);
        }
    }

    public void navigateToAccountManager(View view) {
        if(account.getRole().toString().equals("admin")){
            Intent intent = new Intent(this, AdminViewUserListActivity.class);
            startActivity(intent);
        }
    }
}
