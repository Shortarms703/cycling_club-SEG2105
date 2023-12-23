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

public class ClubEventTable {
    public static final String TABLE_NAME = "club_event_table";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EVENT_TYPE = "event_type";
    public static final String COLUMN_CLUB_ACCOUNT = "club_account";
    public static final String COLUMN_EVENT_DIFF = "event_diff";
    public static final String COLUMN_EVENT_ROUTE = "event_route";
    public static final String COLUMN_EVENT_FEE = "event_fee";
    public static final String COLUMN_MAX_NUM_PARTICIPANTS = "MAX_NUM_PARTICIPANTS";
    public static final String COLUMN_NUM_PARTICIPANTS = "num_participants";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY," +
            COLUMN_EVENT_TYPE + " INTEGER," +       // Will be a foreign key to event table
            COLUMN_CLUB_ACCOUNT + " INTEGER," +     // Will be a foreign key to account table
            COLUMN_EVENT_DIFF + " TEXT," +
            COLUMN_EVENT_ROUTE + " TEXT," +
            COLUMN_EVENT_FEE + " DOUBLE," +
            COLUMN_MAX_NUM_PARTICIPANTS + " INTEGER," +
            COLUMN_NUM_PARTICIPANTS + " INTEGER, " +
            "FOREIGN KEY (" + COLUMN_EVENT_TYPE + ") REFERENCES " + EventTable.TABLE_NAME + "(" + EventTable.COLUMN_ID + ") ON DELETE CASCADE, " +
            "FOREIGN KEY (" + COLUMN_CLUB_ACCOUNT + ") REFERENCES " + CyclingClubTable.TABLE_NAME + "(" + CyclingClubTable.COLUMN_ID + ") ON DELETE CASCADE" +
                    ")";

    public static final String JOIN_RESULT_ALL = "SELECT * FROM " + TABLE_NAME +
            " INNER JOIN " + EventTable.TABLE_NAME + " ON " +
            TABLE_NAME + "." + COLUMN_EVENT_TYPE + "=" +
            EventTable.TABLE_NAME + "." + EventTable.COLUMN_ID;

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static ArrayList<ClubEvent> getClubEventsFromDatabase(DatabaseHelper dbHelper){
        ArrayList<ClubEvent> eventList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(JOIN_RESULT_ALL, null);

        while (cursor.moveToNext() && cursor.getCount()!=0){
            @SuppressLint("Range") int clubEventID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") int eventType = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE));
            @SuppressLint("Range") String eventTypeName = cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_EVENT_NAME));
            @SuppressLint("Range") int clubID = cursor.getInt(cursor.getColumnIndex(COLUMN_CLUB_ACCOUNT));
            @SuppressLint("Range") String diff = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DIFF));
            @SuppressLint("Range") String route = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_ROUTE));
            @SuppressLint("Range") Double fee = cursor.getDouble(cursor.getColumnIndex(COLUMN_EVENT_FEE));
            @SuppressLint("Range") int maxNumParticipants = cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_NUM_PARTICIPANTS));
            @SuppressLint("Range") int numParticipants = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_PARTICIPANTS));

            ClubEvent event = new ClubEvent(eventType, clubID, diff, route, fee, maxNumParticipants, numParticipants);

            event.setClubEventID(clubEventID);
            event.setEventTypeName(eventTypeName);

            eventList.add(event);
        }
        cursor.close();
        return eventList;
    }

    public static ArrayList<ClubEvent> getEventsGivenClubFromDatabase(int id, DatabaseHelper dbHelper){
        ArrayList<ClubEvent> eventList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(JOIN_RESULT_ALL +
                " WHERE club_account=" + id, null);

        while (cursor.moveToNext() && cursor.getCount()!=0){
            @SuppressLint("Range") int clubEventID = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") int eventType = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_TYPE));
            @SuppressLint("Range") String eventTypeName = cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_EVENT_NAME));
            @SuppressLint("Range") int clubID = cursor.getInt(cursor.getColumnIndex(COLUMN_CLUB_ACCOUNT));
            @SuppressLint("Range") String diff = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DIFF));
            @SuppressLint("Range") String route = cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_ROUTE));
            @SuppressLint("Range") Double fee = cursor.getDouble(cursor.getColumnIndex(COLUMN_EVENT_FEE));
            @SuppressLint("Range") int maxNumParticipants = cursor.getInt(cursor.getColumnIndex(COLUMN_MAX_NUM_PARTICIPANTS));
            @SuppressLint("Range") int numParticipants = cursor.getInt(cursor.getColumnIndex(COLUMN_NUM_PARTICIPANTS));

            ClubEvent event = new ClubEvent(eventType, clubID, diff, route, fee, maxNumParticipants, numParticipants);

            event.setClubEventID(clubEventID);
            event.setEventTypeName(eventTypeName);

            eventList.add(event);
        }
        cursor.close();
        return eventList;
    }

    public static ClubEvent addClubEvent(ClubEvent clubEvent, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_TYPE, clubEvent.getEventType());
        values.put(COLUMN_CLUB_ACCOUNT, clubEvent.getClubOwnerID());
        values.put(COLUMN_EVENT_DIFF, clubEvent.getDifficulty());
        values.put(COLUMN_EVENT_ROUTE, clubEvent.getRoute());
        values.put(COLUMN_EVENT_FEE, clubEvent.getFee());
        values.put(COLUMN_MAX_NUM_PARTICIPANTS, clubEvent.getMaxNumParticipants());
        values.put(COLUMN_NUM_PARTICIPANTS, clubEvent.getNumParticipants());

        long newRowID = db.insert(TABLE_NAME, null, values);

        if(newRowID!=-1){
            Cursor cursor = db.rawQuery(JOIN_RESULT_ALL + " WHERE " + TABLE_NAME +
                    ".id=" + newRowID, null);
            if(cursor.moveToFirst()){
                @SuppressLint("Range") String eventTypeName = cursor.getString(cursor.getColumnIndex(EventTable.COLUMN_EVENT_NAME));

                clubEvent.setEventTypeName(eventTypeName);
                clubEvent.setClubEventID((int) newRowID);
            }
            cursor.close();
        }

        db.close();

        return clubEvent;
    }

    public static void updateClubEvent( ClubEvent updatedValues, DatabaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_DIFF, updatedValues.getDifficulty());
        values.put(COLUMN_EVENT_ROUTE, updatedValues.getRoute());
        values.put(COLUMN_EVENT_FEE, updatedValues.getFee());
        values.put(COLUMN_MAX_NUM_PARTICIPANTS, updatedValues.getMaxNumParticipants());
        //values.put(COLUMN_NUM_PARTICIPANTS, updatedValues.getNumParticipants());

        int id = updatedValues.getClubEventID();

        db.update(TABLE_NAME, values, "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public static void deleteClubEvent(int id, DatabaseHelper dbHelper){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME,"id=?", new String[]{String.valueOf(id)});
    }




}
