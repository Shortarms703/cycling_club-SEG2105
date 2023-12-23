package com.example.cyclingclub.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.cyclingclub.accounts.CyclingClubAccount;

public class CyclingClubTable {
    public static final String TABLE_NAME = "cycling_club_accounts";
    public static final String COLUMN_ID = "id"; // reference to accounts table id column
    public static final String COLUMN_CLUBNAME = "clubName";
    public static final String COLUMN_PHONENUMBER = "phoneNumber";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_POSTALCODE = "postalCode";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_CLUBNAME + " TEXT,"
                    + COLUMN_PHONENUMBER + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_POSTALCODE + " TEXT,"
                    + "FOREIGN KEY (" + COLUMN_ID + ") REFERENCES " + AccountTable.TABLE_NAME + "(" + AccountTable.COLUMN_ID + ") ON DELETE CASCADE"
                    + ")";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;



    public static void addAccount(CyclingClubAccount account, SQLiteDatabase db) {
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("password", account.getPassword());
        values.put("email", account.getEmail());
        values.put("firstname", account.getFirstName());
        values.put("lastname", account.getLastName());
        values.put("role", account.getRole().toString());

        long newRowID = db.insert(AccountTable.TABLE_NAME, null, values);

        ContentValues cyclingClubValues = new ContentValues();
        cyclingClubValues.put("id", newRowID);
        cyclingClubValues.put("clubName", account.getClubName());
        cyclingClubValues.put("phoneNumber", account.getPhoneNumber());
        cyclingClubValues.put("address", account.getAddress());
        cyclingClubValues.put("postalCode", account.getPostalCode());

        db.insert(CyclingClubTable.TABLE_NAME, null, cyclingClubValues);
    }
}
