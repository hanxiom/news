package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.news.Contract.Entry.COLUMN_NAME_AUTHOR;
import static com.example.news.Contract.Entry.COLUMN_NAME_CONTENT;
import static com.example.news.Contract.Entry.COLUMN_NAME_DATE;
import static com.example.news.Contract.Entry.COLUMN_NAME_SOURCE;
import static com.example.news.Contract.Entry.COLUMN_NAME_TITLE;
import static com.example.news.Contract.Entry.COLUMN_NAME_URL;
import static com.example.news.Contract.Entry.TABLE_NAME;
import static com.example.news.Contract.SQL_CREATE_ENTRIES;
import static com.example.news.Contract.SQL_DELETE_ENTRIES;

public class DbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "News.db";

    DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    boolean insertRow(String title, String source, String author, String date, String content, String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_TITLE, title);
        values.put(COLUMN_NAME_SOURCE, source);
        values.put(COLUMN_NAME_AUTHOR, author);
        values.put(COLUMN_NAME_DATE, date);
        values.put(COLUMN_NAME_CONTENT, content);
        values.put(COLUMN_NAME_URL, url);

        long newRowId = db.insert(TABLE_NAME, null, values);
        // Retourne si l'insertion s'est bien passée (true) ou non (false)
        return newRowId != -1;
    }
}
