package com.example.news;

import android.provider.BaseColumns;

final class Contract {

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Entry.TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_TITLE + " TEXT," +
                    Entry.COLUMN_NAME_SOURCE + " TEXT," +
                    Entry.COLUMN_NAME_AUTHOR + " TEXT," +
                    Entry.COLUMN_NAME_DATE + " TEXT," +
                    Entry.COLUMN_NAME_CONTENT + " TEXT," +
                    Entry.COLUMN_NAME_IMAGE + " TEXT," +
                    Entry.COLUMN_NAME_URL + " TEXT)";
    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Entry.TABLE_NAME;

    private Contract() {
    }

    /* Inner class that defines the table contents */
    static class Entry implements BaseColumns {
        static final String TABLE_NAME = "favorite";
        static final String COLUMN_NAME_TITLE = "title";
        static final String COLUMN_NAME_SOURCE = "source";
        static final String COLUMN_NAME_AUTHOR = "author";
        static final String COLUMN_NAME_DATE = "date";
        static final String COLUMN_NAME_CONTENT = "content";
        static final String COLUMN_NAME_IMAGE = "image";
        static final String COLUMN_NAME_URL = "url";
    }
}
