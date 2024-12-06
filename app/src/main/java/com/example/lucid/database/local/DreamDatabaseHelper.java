package com.example.lucid.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Date;

public class DreamDatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    private static final String DATABASE_NAME = "Lucid.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "dreams";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_MOOD = "mood";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_IS_LUCID = "islucid";


    public DreamDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                       "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                       COLUMN_TITLE + " TEXT, " +
                       COLUMN_DESCRIPTION + " TEXT, " +
                       COLUMN_MOOD + " TEXT, " +
                       COLUMN_DATE + " TEXT, " +
                       COLUMN_IS_LUCID + " INTEGER" + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addDream(String title, String description, String mood, Date date, boolean isLucid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_MOOD, mood);
        contentValues.put(COLUMN_DATE, date.toString());
        contentValues.put(COLUMN_IS_LUCID, isLucid);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            Toast.makeText(context, "Failed to add the dream!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Dream added successfully!", Toast.LENGTH_SHORT).show();
    }

    public void updateDream(int id, String newTitle, String newDescription, String newMood, boolean newIsLucid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, newTitle);
        contentValues.put(COLUMN_DESCRIPTION, newDescription);
        contentValues.put(COLUMN_MOOD, newMood);
        contentValues.put(COLUMN_IS_LUCID, newIsLucid);

        db.update(TABLE_NAME, contentValues, "WHERE " + COLUMN_ID + " == " + String.valueOf(id), new String[1]);
    }
}
