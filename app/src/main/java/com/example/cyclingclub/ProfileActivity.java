package com.example.cyclingclub;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.cyclingclub.accounts.Account;

public class ProfileActivity extends AppCompatActivity {

    Account account;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    account = (Account) result.getData().getSerializableExtra("account");
                    updateProfileInfo();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        account = (Account) intent.getSerializableExtra("account");

        TextView firstName = findViewById(R.id.textView2);
        TextView role = findViewById(R.id.textView4);

        firstName.setText(firstName.getText().toString().replace("[firstname]", account.getFirstName()));
        role.setText(role.getText().toString().replace("[role]", account.getRole().toString()));

        updateProfileInfo();
    }

    private void updateProfileInfo() {
        TextView socialMedia = findViewById(R.id.textView52);
        TextView mainContact = findViewById(R.id.textView53);
        TextView phoneNumber = findViewById(R.id.textView55);
        Button searchEventButton = findViewById(R.id.search_event);

        System.out.println("account.getCompletedProfileInfo() = " + account.getCompletedProfileInfo());
        System.out.println("account.getSocialMedia() = " + account.getSocialMedia());
        if (account.getCompletedProfileInfo() == 1) {
            findViewById(R.id.c_complete_profile).setVisibility(View.INVISIBLE);
            socialMedia.setText("Social media: " + account.getSocialMedia());
            mainContact.setText("Main contact: " + account.getMainContact());
            phoneNumber.setText("Phone number: " + account.getPhoneNumber());

            System.out.println("Setting visible");

            for (TextView textView : new TextView[]{socialMedia, mainContact, phoneNumber}) {
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                textView.setLayoutParams(params);
                textView.setVisibility(View.VISIBLE);
            }
        }
        else {
            System.out.println("setting invisible");
            for (TextView textView : new TextView[]{socialMedia, mainContact, phoneNumber}) {
                ViewGroup.LayoutParams params = textView.getLayoutParams();
                params.height = 0;
                textView.setLayoutParams(params);
                textView.setVisibility(View.INVISIBLE);
            }
        }


    }

    /**
     * creates an onClick "continue" function which brings an admin to the event type page and lets them add/edit/delete
     */
    public void navigateToEventManager(View view) {
        if (account.getRole().toString().equals("admin")){
            Intent intent = new Intent(this, EventManagerActivity.class);
            startActivity(intent);
        }
        if (account.getRole().toString().equals("club")) {
            Intent intent = new Intent(this, ClubEventListActivity.class);
            intent.putExtra("accountID", account.getID());
            startActivity(intent);
        }
    }

    public void navigateToCompleteProfile(View view) {
        Intent intent = new Intent(this, CompleteProfileActivity.class);
        intent.putExtra("account", account);

        activityResultLauncher.launch(intent);
    }

    // Method to start the new activity
    public void navigateToSearchEventActivity(View view) {
        Intent intent = new Intent(this, SearchEventActivity.class);
        startActivity(intent);
    }
}