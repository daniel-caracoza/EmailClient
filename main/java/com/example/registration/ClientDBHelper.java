package com.example.registration;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;
import java.util.List;

public class ClientDBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    //table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_INBOX = "inbox";
    private static final String TABLE_SENT = "sent";
    private static final String TABLE_IMPORTANT = "important";
    private static final String TABLE_TRASH = "trash";
    private static final String TABLE_DRAFTS = "drafts";



    // Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_EMAIL_ID = "email_id";
    private static final String COLUMN_RECIPIENT = "recipient";
    private static final String COLUMN_SUBJECT = "subject";
    private static final String COLUMN_BODY = "body";
    private static final String COLUMN_DATE = "date";
    // create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";
    private String CREATE_INBOX = "CREATE TABLE " + TABLE_INBOX + "("
            + COLUMN_EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_BODY + " TEXT," + COLUMN_DATE + " TEXT" + ")";
    private String CREATE_SENT = "CREATE TABLE " + TABLE_SENT + "("
            + COLUMN_EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_BODY + " TEXT," + COLUMN_DATE + " TEXT" + ")";
    private String CREATE_IMPORTANT = "CREATE TABLE " + TABLE_IMPORTANT + "("
            + COLUMN_EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_BODY + " TEXT," + COLUMN_DATE + " TEXT" + ")";
    private String CREATE_TRASH = "CREATE TABLE " + TABLE_DRAFTS + "("
            + COLUMN_EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_BODY + " TEXT," + COLUMN_DATE + " TEXT" + ")";
    private String CREATE_DRAFTS = "CREATE TABLE " + TABLE_TRASH + "("
            + COLUMN_EMAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_RECIPIENT + " TEXT,"
            + COLUMN_SUBJECT + " TEXT," + COLUMN_BODY + " TEXT," + COLUMN_DATE + " TEXT" + ")";
    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    /**
     * Constructor
     *
     * @param context
     */
    public ClientDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_INBOX);
        db.execSQL(CREATE_SENT);
        db.execSQL(CREATE_IMPORTANT);
        db.execSQL(CREATE_TRASH);
        db.execSQL(CREATE_DRAFTS);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }
   /**This method adds emails to the specified table in the database

    */
    public void addEmail(EmailDetails email, String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPIENT, email.getSender());
        values.put(COLUMN_SUBJECT, email.getTitle());
        values.put(COLUMN_BODY, email.getSummary());
        values.put(COLUMN_DATE, email.getTime());

        db.insert(table_name, null, values);
        db.close();

    }
    /**
     * change the user name
     */
    public User getUser(String username){
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " =?";
        String[] selectionArgs = {username};
        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                selection,        //columns for the WHERE clause
                selectionArgs,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                null); //The sort order

        User user = new User();
        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
        }
        cursor.close();
        db.close();
        return user;
    }
    /**
     * This method will fetch all the emails from a given table to display in the recycler view
     */
    public List<EmailDetails> getEmails(String table_name){
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                COLUMN_EMAIL_ID,
                COLUMN_RECIPIENT,
                COLUMN_SUBJECT,
                COLUMN_BODY,
                COLUMN_DATE
        };
        List<EmailDetails> emaillist = new ArrayList<>();
        Cursor cursor = db.query(table_name,
                columns,
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            do {
                EmailDetails email = new EmailDetails();
                        email.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL_ID))));
                        email.setSender(cursor.getString(cursor.getColumnIndex(COLUMN_RECIPIENT)));
                        email.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_SUBJECT)));
                        email.setSummary(cursor.getString(cursor.getColumnIndex(COLUMN_BODY)));
                        email.setTime(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));
                        emaillist.add(email);
            }while(cursor.moveToNext());
        }
        return emaillist;
    }
    public String getName(String username){
        String name = "";
        String[] columns ={
                COLUMN_USER_NAME
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            name = cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME));
        }
        cursor.close();
        db.close();
        return name;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }
    public void updatePassword(String username){
        SQLiteDatabase db = this.getWritableDatabase();

        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    public void deleteEmail(int id, String table_name){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table_name, COLUMN_EMAIL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}