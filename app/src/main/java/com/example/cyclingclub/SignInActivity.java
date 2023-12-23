package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cyclingclub.accounts.Account;
import com.example.cyclingclub.accounts.AdminAccount;
import com.example.cyclingclub.accounts.CyclingClubAccount;
import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.accounts.Role;
import com.example.cyclingclub.database.AccountTable;
import com.example.cyclingclub.database.DatabaseHelper;

public class SignInActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        dbHelper = new DatabaseHelper(this);
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    public void signInButton(View view) {
        EditText usernameField = findViewById(R.id.textUsername);
        EditText passwordField = findViewById(R.id.textPassword);

        String username = usernameField.getText().toString();
        String password = passwordField.getText().toString();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                AccountTable.COLUMN_ID,
                AccountTable.COLUMN_USERNAME,
                AccountTable.COLUMN_PASSWORD,
                AccountTable.COLUMN_ROLE,
                AccountTable.COLUMN_EMAIL,
                AccountTable.COLUMN_FIRSTNAME,
                AccountTable.COLUMN_LASTNAME,
                AccountTable.COLUMN_COMPLETED_PROFILE_INFO,
                AccountTable.COLUMN_SOCIAL_MEDIA,
                AccountTable.COLUMN_MAIN_CONTACT,
                AccountTable.COLUMN_PHONE_NUMBER,
        };

        String selection = AccountTable.COLUMN_USERNAME + " = ? AND " + AccountTable.COLUMN_PASSWORD + " = ?";
        String[] selectionArgs = {username, password};

        Cursor cursor = db.query(
                AccountTable.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            int IDFromDB = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_ID)));
            String firstNameFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_FIRSTNAME));
            String lastNameFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_LASTNAME));
            String usernameFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_USERNAME));
            String passwordFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_PASSWORD));
            String emailFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_EMAIL));
            String roleFromDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_ROLE));
            int completedProfileInfoFromDB = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_COMPLETED_PROFILE_INFO)));
            String socialMediaDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_SOCIAL_MEDIA));
            String mainContactDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_MAIN_CONTACT));
            String phoneNumberDB = cursor.getString(cursor.getColumnIndexOrThrow(AccountTable.COLUMN_PHONE_NUMBER));

            cursor.close();
            db.close();

            Account account;

            if (roleFromDB.equals("club")) {
                account = new CyclingClubAccount(usernameFromDB, passwordFromDB, emailFromDB, firstNameFromDB, lastNameFromDB, Role.valueOf(roleFromDB), "", "", "");
            }
            else if (roleFromDB.equals("participant")) {
                account = new ParticipantAccount(usernameFromDB, passwordFromDB, emailFromDB, firstNameFromDB, lastNameFromDB, Role.valueOf(roleFromDB), "", "");
            }
            else if (roleFromDB.equals("admin")) {
                account = new AdminAccount(usernameFromDB, passwordFromDB, emailFromDB, firstNameFromDB, lastNameFromDB, Role.valueOf(roleFromDB));
            }
            else {
                Toast.makeText(this, "Invalid role", Toast.LENGTH_LONG).show();
                return;
            }

            account.setID(IDFromDB);
            account.setCompletedProfileInfo(completedProfileInfoFromDB);
            account.setSocialMedia(socialMediaDB);
            account.setMainContact(mainContactDB);
            account.setPhoneNumber(phoneNumberDB);

            Intent intent;
            if (roleFromDB.equals("club") || roleFromDB.equals("participant")) {
                intent = new Intent(this, ProfileActivity.class);
            }
            else {
                intent = new Intent(this, AdminProfileActivity.class);
            }

            intent.putExtra("account", account);
            startActivity(intent);


        }
        else {
            cursor.close();
            db.close();

            Toast.makeText(this, "Invalid username or password", Toast.LENGTH_LONG).show();
        }


    }
}