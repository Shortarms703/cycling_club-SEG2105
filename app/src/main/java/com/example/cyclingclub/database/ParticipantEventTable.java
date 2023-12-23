package com.example.cyclingclub.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.cyclingclub.ClubEventListActivity;
import com.example.cyclingclub.events.ClubEvent;
import com.example.cyclingclub.events.Event;

import java.util.ArrayList;
import java.util.List;

public class ParticipantEventTable {

    public static final String TABLE_NAME = "participant_events";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER = "user";
    public static final String COLUMN_CLUBID = "clubID";
    public static final String COLUMN_EVENTID = "eventID";
    public static final String COLUMN_EVENTNAME = "eventName";

    public static final String CREATE_TABLE=
            "CREATE TABLE "+ TABLE_NAME +" (" +
            COLUMN_ID+" INTEGER PRIMARY KEY,"+
            COLUMN_USER+ " TEXT," +
            COLUMN_CLUBID+" INTEGER"+
            COLUMN_EVENTID+" INTEGER,"+
            COLUMN_EVENTNAME+" TEXT"
            +")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public static ArrayList<String> getpEvents(DatabaseHelper dbHelper){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        while(cursor.moveToNext() && cursor.getCount()!=0){
            @SuppressLint("Range") String eventname = cursor.getString(cursor.getColumnIndex(COLUMN_EVENTNAME));

            list.add(eventname);
        }
        cursor.close();
        return list;
    }

    public static ClubEvent joinEvent(ClubEvent clubEvent, DatabaseHelper dbHelper, String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, username);
        values.put(COLUMN_EVENTNAME, clubEvent.getEventTypeName());

        long newRowID = db.insert(TABLE_NAME, null, values);
        clubEvent.setClubEventID((int) newRowID);
        db.close();

        return clubEvent;
    }

}
