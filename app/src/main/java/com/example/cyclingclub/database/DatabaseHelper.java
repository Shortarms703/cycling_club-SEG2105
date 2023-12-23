package com.example.cyclingclub.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.cyclingclub.ParticipantEventActivity;
import com.example.cyclingclub.accounts.CyclingClubAccount;
import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.accounts.Role;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 34;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AccountTable.CREATE_TABLE);
        db.execSQL(AccountTable.INSERT_ADMIN_ROW);

        db.execSQL(CyclingClubTable.CREATE_TABLE);

        db.execSQL(ParticipantTable.CREATE_TABLE);

        db.execSQL(EventTable.CREATE_TABLE);

        db.execSQL(ClubEventTable.CREATE_TABLE);

        db.execSQL(ParticipantEventTable.CREATE_TABLE);

        db.execSQL(CommentTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(AccountTable.DROP_TABLE);
        db.execSQL(CyclingClubTable.DROP_TABLE);
        db.execSQL(ParticipantTable.DROP_TABLE);
        db.execSQL(EventTable.DROP_TABLE);
        db.execSQL(ClubEventTable.DROP_TABLE);
        db.execSQL(ParticipantEventTable.DROP_TABLE);
        db.execSQL(CommentTable.DROP_TABLE);

        onCreate(db);

        CyclingClubAccount cyclingClubAccount = new CyclingClubAccount("gccadmin", "GCCRocks!", "cyclingclub@gmail.com", "first", "last", Role.club, "Admin Cycling Club", "123 Main St", "K1M 1M1");
        CyclingClubTable.addAccount(cyclingClubAccount, db);

        ParticipantAccount participantAccount = new ParticipantAccount("cyclingaddict", "cyclingIsLife!", "participant@gmail.com", "first", "last", Role.participant, "03/07/2003", "1");
        ParticipantTable.addAccount(participantAccount, db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
