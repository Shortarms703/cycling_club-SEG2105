package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyclingclub.accounts.Account;
import com.example.cyclingclub.database.DatabaseHelper;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;

public class CompleteProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_profile);
    }
    private Boolean validatePhoneNumber(String phoneNumber) {
        String phonePattern = "^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$";
        return Pattern.matches(phonePattern, phoneNumber);
    }

    public void saveProfileInfo(View v) {
        DatabaseHelper dbHelper = new DatabaseHelper(CompleteProfileActivity.this);

        EditText socialMediaLinkField = findViewById(R.id.textView15);
        EditText mainContactField = findViewById(R.id.textView21);
        EditText phoneNumberField = findViewById(R.id.textView11);

        String socialMediaLink = socialMediaLinkField.getText().toString();
        String mainContact = mainContactField.getText().toString();
        String phoneNumber = phoneNumberField.getText().toString();

        boolean error = false;
        try {
            if (!socialMediaLink.startsWith("http://") && !socialMediaLink.startsWith("https://")) {
                URL url = new URL("http://" + socialMediaLink);
            }
            else {
                URL url = new URL(socialMediaLink);
            }
        } catch (MalformedURLException e) {
            socialMediaLinkField.setError("Invalid link");
            error = true;
        }
        if (socialMediaLink.trim().isEmpty()) {
            socialMediaLinkField.setError("This field cannot be empty");
            error = true;
        }
        if (phoneNumber.trim().isEmpty()) {
            phoneNumberField.setError("This field cannot be empty");
            error = true;
        }
        if (!validatePhoneNumber(phoneNumber)) {
            phoneNumberField.setError("This is not a valid phone number, should be like (###)###-####");
            error = true;
        }
        if(!error){
            Intent intent = getIntent();
            Account account = (Account) intent.getSerializableExtra("account");

            if (account != null) {
                account.addCompletedProfileInformation(dbHelper, socialMediaLink, mainContact, phoneNumber);
            }
            else {
                Toast.makeText(CompleteProfileActivity.this, "Account is null", Toast.LENGTH_SHORT).show();
            }

            intent.putExtra("account", account);
            setResult(RESULT_OK, intent);

            finish();
        }


    }
}