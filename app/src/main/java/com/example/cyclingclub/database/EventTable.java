package com.example.cyclingclub.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

import com.example.cyclingclub.events.*;

import java.util.ArrayList;

public class EventTable {
    public static final String TABLE_NAME="event_type_table";
    public static final String COLUMN_ID="id";
    public static final String COLUMN_EVENT_NAME="eventName";
    public static final String  COLUMN_EVENT_DATE="eventDate";
    public static final String COLUMN_EVENT_TERRAIN="eventTerrain";
    public static final String COLUMN_EVENT_LENGTH="eventLength";
    public static final String COLUMN_EVENT_AGE="eventAge";
    public static final String COLUMN_EVENT_FOCUS="eventFocus";
    public static final String COLUMN_EVENT_DESCRIPTION="eventDescription";
    public static final String COLUMN_EVENT_DISTANCE= "eventDistance";
    public static final String CREATE_TABLE=
            "CREATE TABLE "+ TABLE_NAME +"("
            + COLUMN_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_EVENT_NAME + " TEXT,"
            + COLUMN_EVENT_DATE +" TEXT,"
            + COLUMN_EVENT_TERRAIN + " TEXT,"
            + COLUMN_EVENT_LENGTH + " TEXT,"
            + COLUMN_EVENT_AGE + " TEXT,"
            + COLUMN_EVENT_FOCUS + " TEXT,"
            + COLUMN_EVENT_DESCRIPTION + " TEXT, "
            + COLUMN_EVENT_DISTANCE + " TEXT"
            +")";

    public static final String DROP_TABLE=
            "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public static int addEvent(Event event, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, event.getEventName());
        values.put(COLUMN_EVENT_DATE, event.getEventDate());
        values.put(COLUMN_EVENT_LENGTH, event.getEventLength());
        values.put(COLUMN_EVENT_FOCUS, event.getEventFocus());
        values.put(COLUMN_EVENT_AGE, event.getEventAge());
        values.put(COLUMN_EVENT_TERRAIN, event.getTerrain());
        values.put(COLUMN_EVENT_DESCRIPTION, event.getDescription());
        values.put(COLUMN_EVENT_DISTANCE,event.getDistance());

        long newRowID = db.insert(TABLE_NAME, null, values);

        event.setEventID((int) newRowID);

        db.close();

        return (int) newRowID;
    }

    public static void updateEvent(Event newValues, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EVENT_NAME, newValues.getEventName());
        values.put(COLUMN_EVENT_DATE, newValues.getEventDate());
        values.put(COLUMN_EVENT_LENGTH, newValues.getEventLength());
        values.put(COLUMN_EVENT_FOCUS, newValues.getEventFocus());
        values.put(COLUMN_EVENT_AGE, newValues.getEventAge());
        values.put(COLUMN_EVENT_TERRAIN, newValues.getTerrain());
        values.put(COLUMN_EVENT_DESCRIPTION, newValues.getDescription());
        values.put(COLUMN_EVENT_DISTANCE, newValues.getDistance());

        String pk = newValues.getEventName();
        db.update(TABLE_NAME, values, "eventName=?", new String[]{pk});

        db.close();
    }

    public static void deleteEvent(String eventName, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        db.delete(TABLE_NAME, "eventName=?", new String[]{eventName});
    }

    public static ArrayList<Event> getEventsFromDatabase(DatabaseHelper dbHelper){
        ArrayList<Event> eventList= new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_NAME, null);

        while (cursor.moveToNext() && cursor.getCount()!=0){
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
            @SuppressLint("Range") String eventName= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_NAME));
            @SuppressLint("Range") String eventDate= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DATE));
            @SuppressLint("Range") String eventLength= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_LENGTH));
            @SuppressLint("Range") String eventFocus= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_FOCUS));
            @SuppressLint("Range") String eventAge= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_AGE));
            @SuppressLint("Range") String eventTerrain= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_TERRAIN));
            @SuppressLint("Range") String eventDescription= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DESCRIPTION));
            @SuppressLint("Range") String eventDistance= cursor.getString(cursor.getColumnIndex(COLUMN_EVENT_DISTANCE));

            Event event = new Event(eventName, eventTerrain, eventLength, eventAge, eventFocus, eventDate, eventDescription, eventDistance);
            event.setEventID(id);
            eventList.add(event);
        }
        cursor.close();
        return eventList;
    }

    public static int getAgeRequirement(DatabaseHelper dbHelper, int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_NAME +
                " WHERE " +
                COLUMN_ID + " = " + id;

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        @SuppressLint("Range") int ageRequirement = cursor.getInt(cursor.getColumnIndex(COLUMN_EVENT_AGE));

        return ageRequirement;
    }

}
