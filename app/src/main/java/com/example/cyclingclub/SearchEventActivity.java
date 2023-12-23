package com.example.cyclingclub;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cyclingclub.accounts.Account;
import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.database.AccountTable;
import com.example.cyclingclub.database.ClubEventTable;
import com.example.cyclingclub.database.CommentTable;
import com.example.cyclingclub.database.CyclingClubTable;
import com.example.cyclingclub.database.DatabaseHelper;
import com.example.cyclingclub.database.EventTable;
import com.example.cyclingclub.database.ParticipantEventTable;
import com.example.cyclingclub.database.ParticipantTable;
import com.example.cyclingclub.events.ClubEvent;
import com.example.cyclingclub.events.Event;
import com.example.cyclingclub.events.Comment;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

public class SearchEventActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;
    EditText editTextSearch;

    public ListView events_list;
    public List<ClubEvent> clubEvents;

    public int searchedClubID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        editTextSearch = findViewById(R.id.editTextSearch);
        ListView list = findViewById(R.id.events_list);

        List<String> userData = new ArrayList<>();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userData);

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                CyclingClubTable.COLUMN_ID,
                CyclingClubTable.COLUMN_CLUBNAME
        };
        Cursor cursor = db.query(
                CyclingClubTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        String[] projection2 = {
                EventTable.COLUMN_ID,
                EventTable.COLUMN_EVENT_NAME,
                EventTable.COLUMN_EVENT_LENGTH,
                EventTable.COLUMN_EVENT_AGE,
                EventTable.COLUMN_EVENT_DESCRIPTION
        };

        Cursor cursor2 = db.query(
                EventTable.TABLE_NAME,
                projection2,
                null,
                null,
                null,
                null,
                null
        );

        Integer initialCursor2Position = cursor2.getPosition();


        // Action listener for the search bar
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || (event != null && event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    clearListView(userData,adapter);
                    Integer resultCounter=0;
                    EditText searchEditText = findViewById(R.id.editTextSearch);
                    String searchText = searchEditText.getText().toString();
                    // For some reason cursors must be reset to position 0 but cursor2 must be reset to another position
                    cursor.moveToPosition(0);
                    cursor2.moveToPosition(initialCursor2Position);

                    while (cursor.moveToNext() && cursor.getCount() != 0) {
                        String clubID = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                        String clubName = cursor.getString(cursor.getColumnIndexOrThrow("clubName"));
                        String eventFromClub = "Id: " + clubID + " Club name: " + clubName + " (CLUB)";
                        if(searchText.equals(clubName)){
                            resultCounter++;
                            userData.add(eventFromClub);

                            //add comment info to user data
                            ArrayList<Comment> listComments = CommentTable.getComments(dbHelper, Integer.parseInt(clubID));
                            String comments = "Reviews for club:\n";
                            for(Comment c : listComments){
                                comments += c.getReviewText();
                            }

                            searchedClubID = Integer.parseInt(clubID);

                            userData.add(comments);
                        }
                    }
                    while (cursor2.moveToNext() && cursor2.getCount() != 0) {
                        String eventID = cursor2.getString(cursor2.getColumnIndexOrThrow("id"));
                        String eventName = cursor2.getString(cursor2.getColumnIndexOrThrow("eventName"));
                        String eventLength = cursor2.getString(cursor2.getColumnIndexOrThrow("eventLength"));
                        String eventAge = cursor2.getString(cursor2.getColumnIndexOrThrow("eventAge"));
                        String eventDescription = cursor2.getString(cursor2.getColumnIndexOrThrow("eventDescription"));
                        String eventFromEvent = " Id: " + eventID + " Event name: " + eventName + " (EVENT) \n"
                                + " Length: " + eventLength +"km"+ " Minimum age: " + eventAge + "\n Description: " + eventDescription ;
                        if (searchText.equals(eventName)) {
                            resultCounter++;
                            userData.add(eventFromEvent);

                        }
                    }
                    if (resultCounter==0){
                        userData.add("No results for "+ searchText);
                    }

                    return true;
                }
                else{
                    // IT WILL SHOW EVERYTHING
                }
                return false;
            }
        });
        list.setAdapter(adapter);
    }

    protected void onStart(){
        super.onStart();
        events_list = (ListView) findViewById(R.id.events_list);

        clubEvents = ClubEventTable.getClubEventsFromDatabase(dbHelper);
        events_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                ClubEvent event = clubEvents.get(position);
                showJoinDialog(event);
            }
        });
    }

    public void showJoinDialog(ClubEvent event){
        Dialog dialog = new Dialog(SearchEventActivity.this);
        dialog.setContentView(R.layout.participant_join_event);

        TextView txtUser = dialog.findViewById(R.id.txtUser);
        EditText txtComment = dialog.findViewById(R.id.txtComment);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        Button btnJoin = dialog.findViewById(R.id.btnJoin);
        Button btnSubmitCommentRating = dialog.findViewById(R.id.btnSubmitCommentRating);

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                // ***** FIELD VALIDATION *****
                /*
                 * CASE I: Valid username
                 * confirm age requirement met
                 * CASE II: Invalid username
                 * error message appears: this is not a valid user
                 */

                String username = txtUser.getText().toString();
                int userExists = ParticipantTable.getUserExists(dbHelper, username);

                boolean error = false;

                if (userExists == 0) {
                    txtUser.setError("Invalid Username.");
                    error = true;
                }
                else {
                    String dob = ParticipantTable.getUserDob(dbHelper, username);
                    int age = getAgeFromDob(dob);
                    int eventTypeID = event.getEventType();
                    int ageRequirement = EventTable.getAgeRequirement(dbHelper, eventTypeID);

                    if (age < ageRequirement) {
                        txtUser.setError("Under age requirement: " + ageRequirement + " years.");
                        error = true;
                    }
                }

                if (error == false) {
                    ParticipantEventTable.joinEvent(event, dbHelper, txtUser.getText().toString());
                    Intent intent = new Intent(SearchEventActivity.this, ParticipantEventActivity.class);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }
        });

        btnSubmitCommentRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = txtComment.getText().toString();
                int rating = (int) ratingBar.getRating();

                // ***** FIELD VALIDATION *****
                // reject empty comments

                boolean error = false;

                if (commentText.trim().equals("")) {
                    txtComment.setError("Comment cannot be empty.");
                    error = true;
                }

                if (error == false) {
                    Comment comment = new Comment( rating, commentText, searchedClubID);
                    CommentTable.addComment(comment, dbHelper);

                    Toast.makeText(SearchEventActivity.this, "Submitted Comment: " + comment.getComment() + ", Rating: " + rating, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private int getAgeFromDob(String input) {
        String[] inputArray = input.split("/");
        int year = Integer.valueOf(inputArray[2]);
        int month = Integer.valueOf(inputArray[1]);
        int day = Integer.valueOf(inputArray[0]);

        LocalDate dob = LocalDate.of(year, month, day);

        // Get current date
        LocalDate curDate = LocalDate.now();

        if ((dob != null) && (curDate != null))
        {
            return Period.between(dob, curDate).getYears();
        }
        else
        {
            return 0;
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
    private void clearListView(List<String> dataList,ArrayAdapter<String> adapter ) {
        // Clear the data
        dataList.clear();
        // Tell the adapter the data changed
        adapter.notifyDataSetChanged();
    }

}

