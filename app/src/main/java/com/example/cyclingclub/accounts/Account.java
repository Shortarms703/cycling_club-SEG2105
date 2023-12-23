package com.example.cyclingclub.accounts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.cyclingclub.database.AccountTable;
import com.example.cyclingclub.database.CyclingClubTable;
import com.example.cyclingclub.database.DatabaseHelper;

import java.io.Serializable;
import java.util.regex.Pattern;

public abstract class Account implements Serializable {

    protected int id;
    protected String username;
    protected String password;
    protected String email;
    protected String firstName;
    protected String lastName;
    protected Role role;
    protected int completedProfileInfo;
    private String socialMedia;
    private String mainContact;
    private String phoneNumber;


    public void setID(int id) {
        this.id = id;
    }

    public void setCompletedProfileInfo(int completedProfileInfo) {
        this.completedProfileInfo = completedProfileInfo;
    }

    public int getCompletedProfileInfo() {
        return completedProfileInfo;
    }

    public int getID () {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public abstract void logIn(String username, String password);

    public abstract void logOut();

    public Boolean validateUniqueUsername(DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + AccountTable.TABLE_NAME + " WHERE username = '" + username + "'", null);

        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        } else {
            cursor.close();
            db.close();
            return true;
        }
    }

    public Boolean validatePassword() {
        return password.length() >= 8;
    };

    public Boolean validateEmail() {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        return Pattern.matches(emailPattern, email);
    }

    public void addCompletedProfileInformation(DatabaseHelper dbHelper, String socialMedia, String mainContact, String phoneNumber) {
        this.socialMedia = socialMedia;
        this.mainContact = mainContact;
        this.phoneNumber = phoneNumber;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AccountTable.COLUMN_SOCIAL_MEDIA, socialMedia);
        values.put(AccountTable.COLUMN_MAIN_CONTACT, mainContact);
        values.put(AccountTable.COLUMN_PHONE_NUMBER, phoneNumber);
        values.put(AccountTable.COLUMN_COMPLETED_PROFILE_INFO, 1);

        int rowsUpdated = db.update(AccountTable.TABLE_NAME, values, "id = " + id, null);

        if (rowsUpdated > 0) {
            completedProfileInfo = 1;
        }

        db.close();
    }

    public String getSocialMedia() {
        return socialMedia;
    }

    public void setSocialMedia(String socialMedia) {
        this.socialMedia = socialMedia;
    }

    public String getMainContact() {
        return mainContact;
    }

    public void setMainContact(String mainContact) {
        this.mainContact = mainContact;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}



