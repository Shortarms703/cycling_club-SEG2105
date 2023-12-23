package com.example.cyclingclub.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cyclingclub.accounts.CyclingClubAccount;
import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.events.Event;

import java.util.ArrayList;

public class ParticipantTable {
    public static final String TABLE_NAME = "participant_accounts";
    public static final String COLUMN_ID = "id"; // reference to accounts table id column
    public static final String COLUMN_DATEOFBIRTH = "dateOfBirth";
    public static final String COLUMN_LEVEL = "level";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_DATEOFBIRTH + " TEXT,"
                    + COLUMN_LEVEL + " TEXT,"
                    + "FOREIGN KEY (" + COLUMN_ID + ") REFERENCES " + AccountTable.TABLE_NAME + "(" + AccountTable.COLUMN_ID + ") ON DELETE CASCADE"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static void addAccount(ParticipantAccount account, SQLiteDatabase db) {

        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("email", account.getEmail());
        values.put("firstname", account.getFirstName());
        values.put("lastname", account.getLastName());
        values.put("role", account.getRole().toString());

        long newRowID = db.insert(AccountTable.TABLE_NAME, null, values);

        ContentValues participantValues = new ContentValues();
        participantValues.put("id", newRowID);
        participantValues.put("dateOfBirth", account.getDateOfBirth());
        participantValues.put("level", account.getLevel());

        db.insert(ParticipantTable.TABLE_NAME, null, participantValues);
    }

    public static int getUserExists(DatabaseHelper dbHelper, String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + AccountTable.TABLE_NAME +
                " WHERE " +
                AccountTable.TABLE_NAME + "." + AccountTable.COLUMN_USERNAME +
                " = " + "'" + username + "'";

        Cursor cursor = db.rawQuery(query, null);

        return cursor.getCount();
    }

    public static String getUserDob(DatabaseHelper dbHelper, String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + AccountTable.TABLE_NAME +
                " INNER JOIN " + TABLE_NAME +
                " ON " +
                AccountTable.TABLE_NAME + "." + AccountTable.COLUMN_ID +
                " = " +
                TABLE_NAME + "." + COLUMN_ID +
                " WHERE " +
                AccountTable.TABLE_NAME + "." + AccountTable.COLUMN_USERNAME +
                " = " + "'" + username + "'";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex(COLUMN_DATEOFBIRTH));

        return dob;
    }

}