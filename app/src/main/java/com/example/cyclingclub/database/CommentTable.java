package com.example.cyclingclub.database;

import static com.example.cyclingclub.database.ParticipantEventTable.COLUMN_CLUBID;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.cyclingclub.events.Comment;
import android.content.ContentValues;
import java.util.ArrayList;

public class CommentTable {
    public static final String TABLE_NAME = "club_comments";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CLUBID = "clubID";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_COMMENT = "comment";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_CLUBID + " INTEGER, " +
                    COLUMN_RATING + " INTEGER, " +
                    COLUMN_COMMENT + " TEXT" +
                    ")";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS "+ TABLE_NAME;

    public static ArrayList<Comment> getComments(DatabaseHelper dbHelper, int clubID){
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] selectionArgs = { String.valueOf(clubID) };

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE " + COLUMN_CLUBID + " = ?", selectionArgs);

        while(cursor.moveToNext() && cursor.getCount()!=0){
            @SuppressLint("Range") int rating = cursor.getInt(cursor.getColumnIndex(COLUMN_RATING));
            @SuppressLint("Range") String commentText = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENT));

            Comment comment = new Comment(rating, commentText, clubID);
            commentList.add(comment);
        }
        cursor.close();
        return commentList;
    }

    public static long addComment(Comment comment, DatabaseHelper dbHelper) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_CLUBID, comment.getClubID());
        values.put(COLUMN_RATING, comment.getRating());
        values.put(COLUMN_COMMENT, comment.getComment());

        long newRowID = db.insert(TABLE_NAME, null, values);

        db.close();

        return newRowID;

    }


}
