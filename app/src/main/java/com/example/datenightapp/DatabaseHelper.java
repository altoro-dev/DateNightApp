package com.example.datenightapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // DATABASE CONSTANTS
    private static final String DATABASE_NAME = "DateNight.db";
    private static final int DATABASE_VERSION = 1;

    // TABLE AND COLUMN CONSTANTS
    // Table name for storing date options
    private static final String TABLE_OPTIONS = "options";
    // Primary key column - auto increments for each new row
    private static final String COLUMN_ID = "id";
    // Column for option text
    private static final String COLUMN_NAME = "name";
    // Column for category type
    private static final String COLUMN_TYPE = "type";

    // Constructor - Called when creating an instance of DatabaseHelper
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called only when database is created for the first time
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_OPTIONS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," // Auto generates IDs
                + COLUMN_NAME + " TEXT," // Option text
                + COLUMN_TYPE + " TEXT" // Category type
                + ")";

        // Executes any SQL statement
        db.execSQL(CREATE_TABLE);

        // Add starter data so users aren't greeted with empty lists
        addDefaultOptions(db);
    }

    // Populates database with initial data
    private void addDefaultOptions(SQLiteDatabase db) {
        // Default activities - common date ideas
        insertOption(db, "Watch a movie", "activity");
        insertOption(db, "Go for a walk", "activity");
        insertOption(db, "Play Happy Little Dinosaurs", "activity");
        insertOption(db, "Cook together", "activity");
        insertOption(db, "Play Minecraft", "activity");

        // Default food options - popular choices
        insertOption(db, "Pizza", "food");
        insertOption(db, "Culvers", "food");
        insertOption(db, "McDonalds", "food");
        insertOption(db, "Canes", "food");
        insertOption(db, "Chic-fil-A", "food");
    }

    // Helper method to insert a single option
    private void insertOption(SQLiteDatabase db, String name, String type) {
        // Maps column names to value
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);

        db.insert(TABLE_OPTIONS, null, values);
    }

    // Called when database version number increases
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPTIONS);

        // Recreate table with new schema
        onCreate(db);
    }

    // ========== CRUD OPERATIONS ==========
    // Create, Read, Update, Delete
    // CREATE operation - 'C' in CRUD
    public long addOption(String name, String type) {
        // Opens database for read/write
        SQLiteDatabase db = this.getWritableDatabase();

        // ContentValues = key-value pairs for database row
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_TYPE, type);

        long id = db.insert(TABLE_OPTIONS, null, values);

        // Close database when done to free resources
        db.close();

        return id;
    }

    //READ operation - 'R' in CRUD
    public List<DateOption> getOptionsByType(String type) {
        // ArrayList to store result
        List<DateOption> options = new ArrayList<>();

        // Opens for read-only acces
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_OPTIONS,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_TYPE},
                COLUMN_TYPE + "=?",
                new String[]{type},
                null,
                null,
                COLUMN_NAME + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                //Create DateOption object from current row
                DateOption option = new DateOption(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2)
                );
                options.add(option);
            } while (cursor.moveToNext());
        }

        // Always close cursor and database
        cursor.close();
        db.close();

        return options;
    }

    // UPDATE operation - 'U' in CRUD
    public int updateOption(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues with new data
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);

        int result = db.update(
                TABLE_OPTIONS,
                values,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
        return result;
    }

    // DELETE operation - 'D' in CRUD
    public void deleteOption(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(
                TABLE_OPTIONS,
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    // Utility method to count options
    public int getOptionCount(String type) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query only IDs (don't need full data)
        Cursor cursor = db.query(
                TABLE_OPTIONS,
                new String[]{COLUMN_ID},
                COLUMN_TYPE + "=?",
                new String[]{type},
                null, null, null
        );

        // getCount() returns number of rows in result
        int count = cursor.getCount();

        cursor.close();
        db.close();

        return count;
    }

    /// POTENTIAL IMPROVEMENTS:
    /// 1. Batch operations for better performance:
    ///    db.beginTransaction();
    ///    try {
    ///        // multiple operations
    ///        db.setTransactionSuccessful();
    ///    } finally {
    ///        db.endTransaction();
    ///    }
    /// 2. Async operations to avoid blocking UI thread:
    ///    Use AsyncTask, Executors, or Room library
    /// 3. Error handling:
    ///    Try-catch blocks for SQLiteException
    /// 4. Add search/filter methods:
    ///    searchByName(String query)
    ///    getRecentOptions(int limit)
    /// 5. Use Room library (modern Android approach):
    ///    - Less boilerplate code
    ///    - Compile-time SQL verification
    ///    - Built-in LiveData support
}
