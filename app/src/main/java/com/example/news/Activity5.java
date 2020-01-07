package com.example.news;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Activity5 extends AppCompatActivity {
    protected ListView favList;

    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> sources = new ArrayList<>();
    ArrayList<String> authors = new ArrayList<>();
    ArrayList<String> dates = new ArrayList<>();
    ArrayList<String> contents = new ArrayList<>();
    ArrayList<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        DbHelper dbHelper = ((NewsApp) getApplication()).dbHelper;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                Contract.Entry.COLUMN_NAME_TITLE,
                Contract.Entry.COLUMN_NAME_SOURCE,
                Contract.Entry.COLUMN_NAME_AUTHOR,
                Contract.Entry.COLUMN_NAME_DATE,
                Contract.Entry.COLUMN_NAME_CONTENT,
                Contract.Entry.COLUMN_NAME_URL,
        };

        Cursor cursor = db.query(
                Contract.Entry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        favList = findViewById(R.id.articlesList);

        while (cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_TITLE));
            titles.add(title);
            String source = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_SOURCE));
            sources.add(source);
            String author = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_AUTHOR));
            authors.add(author);
            String date = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_DATE));
            dates.add(date);
            String content = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_CONTENT));
            contents.add(content);
            String url = cursor.getString(
                    cursor.getColumnIndexOrThrow(Contract.Entry.COLUMN_NAME_URL));
            urls.add(url);
        }
        cursor.close();

        // Creation du ListView et de son ArrayAdapter
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<>(
                favList.getContext(),
                R.layout.article,
                R.id.textView
        );
        favList.setAdapter(fieldsAdapter);

        for (int i = 0; i < titles.size(); i++) {
            fieldsAdapter.add("\n"
                    + "Titre : " + titles.get(i) + "\n\n"
                    + "Source : " + sources.get(i) + "\n\n"
                    + "Date : " + dates.get(i) + "\n"
            );
        }

        favList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent activity6 = new Intent(Activity5.this, Activity6.class);
                activity6.putExtra("title", titles.get(position));
                activity6.putExtra("name", sources.get(position));
                activity6.putExtra("author", authors.get(position));
                activity6.putExtra("date", dates.get(position));
                activity6.putExtra("content", contents.get(position));
                activity6.putExtra("url", urls.get(position));
                startActivity(activity6);
            }
        });
    }
}
