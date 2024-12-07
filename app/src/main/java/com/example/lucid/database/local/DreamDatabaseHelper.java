package com.example.lucid.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.lucid.Dream;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
                       COLUMN_DATE + " INTEGER, " + // The date is stored as java.util.Date.getTime()
                       COLUMN_IS_LUCID + " INTEGER" + ")"; // isLucid is stored as 0 or 1
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public int getDreamCount() {
        String query = "SELECT COUNT(*) FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        int result = 0;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToNext()) {
            result = cursor.getInt(0);
        }
        cursor.close();

        return result;
    }

    public List<Dream> getDreams() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        if (db == null)
            return new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        List<Dream> list = new ArrayList<>();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Dream dream = new Dream(cursor.getInt(0),
                                        cursor.getString(1),
                                        cursor.getString(2),
                                        cursor.getString(3),
                                        new Date(cursor.getInt(4)),
                                 cursor.getInt(5) == 1);
                list.add(dream);
            }
            cursor.close();
        }

        return list;
    }

    public long addDream(String title, String description, String mood, Date date, boolean isLucid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_DESCRIPTION, description);
        contentValues.put(COLUMN_MOOD, mood);
        contentValues.put(COLUMN_DATE, date.getTime());
        contentValues.put(COLUMN_IS_LUCID, isLucid);

        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1)
            Toast.makeText(context, "Failed to add the dream!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Dream added successfully!", Toast.LENGTH_SHORT).show();

        return result;
    }

    public void updateDream(int id, String newTitle, String newDescription, String newMood, boolean newIsLucid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, newTitle);
        contentValues.put(COLUMN_DESCRIPTION, newDescription);
        contentValues.put(COLUMN_MOOD, newMood);
        contentValues.put(COLUMN_IS_LUCID, newIsLucid);

        long result = db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[] {String.valueOf(id)});
        if (result == 0) {
            Toast.makeText(context, "Could not update the dream!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Dream updated successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteDream(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[] {String.valueOf(id)});
        if (result == 0) {
            Toast.makeText(context, "Failed to delete the dream!", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(context, "Dream deleted successfully!", Toast.LENGTH_SHORT).show();
        }
    }
}
