package com.example.cyclingclub;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.cyclingclub.accounts.ParticipantAccount;
import com.example.cyclingclub.database.AccountTable;
import com.example.cyclingclub.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class AdminViewUserListActivity extends AppCompatActivity {
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbHelper = new DatabaseHelper(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_user_list);

        List<String> userData = new ArrayList<>();
        List<String> userNames = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                AccountTable.COLUMN_USERNAME,
                AccountTable.COLUMN_ROLE
        };
        Cursor cursor = db.query(
                AccountTable.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        while (cursor.moveToNext() && cursor.getCount() != 0) {
            String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String role = cursor.getString(cursor.getColumnIndexOrThrow("role"));

            String user = "User: " + username + " Role:" + role;
            userData.add(user);
            userNames.add(username);
        }


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userData);
        ListView list = findViewById(R.id.user_list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(((adapterView, view, i, l) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete Account");
            builder.setMessage("Would you like to delete this user's account?");

            builder.setPositiveButton("Yes", (dialog, which) -> {
                String username = userNames.get(i);
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                db1.delete(AccountTable.TABLE_NAME, "username = ?", new String[]{username});
                db1.close();
                userData.remove(i);
                userNames.remove(i);
                adapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("No", (dialog, which) -> {
                // Your action if the user clicks "No" or dismisses the dialog
            });

            AlertDialog dialog = builder.create();
            dialog.show();
        }));

    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}