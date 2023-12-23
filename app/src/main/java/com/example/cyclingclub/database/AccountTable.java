package com.example.cyclingclub.database;


public class AccountTable {
    public static final String TABLE_NAME = "accounts";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL="email";
    public static final String COLUMN_FIRSTNAME = "firstname";
    public static final String COLUMN_LASTNAME = "lastname";
    public static final String COLUMN_ROLE = "role";
    public static final String COLUMN_COMPLETED_PROFILE_INFO = "completed_profile_info";
    public static final String COLUMN_SOCIAL_MEDIA = "social_media";
    public static final String COLUMN_MAIN_CONTACT = "main_contact";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT,"
                    + COLUMN_EMAIL + " TEXT,"
                    + COLUMN_FIRSTNAME + " TEXT,"
                    + COLUMN_LASTNAME + " TEXT,"
                    + COLUMN_ROLE + " TEXT,"
                    + COLUMN_COMPLETED_PROFILE_INFO + " INTEGER DEFAULT 0,"
                    + COLUMN_SOCIAL_MEDIA + " TEXT,"
                    + COLUMN_MAIN_CONTACT + " TEXT,"
                    + COLUMN_PHONE_NUMBER + " TEXT"
                    + ")";

    public static final String INSERT_ADMIN_ROW =
            "INSERT INTO " + TABLE_NAME + "("
                    + COLUMN_USERNAME + ","
                    + COLUMN_PASSWORD + ","
                    + COLUMN_EMAIL + ","
                    + COLUMN_FIRSTNAME + ","
                    + COLUMN_LASTNAME + ","
                    + COLUMN_ROLE + ")"
                    + " VALUES ('admin', 'admin', 'admin@email.com', 'admin', 'admin', 'admin')";

    public static final String DROP_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME;
}
