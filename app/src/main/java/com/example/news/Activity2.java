package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;


public class Activity2 extends AppCompatActivity {
    protected ListView listView;
    AsyncNewsJSONData task;

    @Override
    public boolean onSupportNavigateUp(){
        // Enables the back button behaviour
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set activity's title
        getSupportActionBar().setTitle("Gros titres");
        // Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        listView = findViewById(R.id.articlesList);

        Intent intent = getIntent();
        String[] params = new String[4];
        params[2] = intent.getStringExtra("sources");
        Log.i("CIO", "la" + params[2]);
        assert params[2] != null;
        if (!params[2].equals("")) {
            params[0] = "";
            params[1] = "";
        } else {
            params[0] = intent.getStringExtra("country");
            params[1] = intent.getStringExtra("category");
            params[2] = "";
        }
        params[3] = intent.getStringExtra("keywords");

        task = new AsyncNewsJSONData(Activity2.this);
        String url = "https://newsapi.org/v2/top-headlines?"
                + "apiKey=245c4cf65f9a4689bb70f5371bd3a642"
                + "&country=" + params[0]
                + "&category=" + params[1]
                + "&sources=" + params[2]
                + "&q=" + params[3];
        Log.i("URL", url);
        task.execute(url);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    String title = task.articles.getJSONObject(position).getString("title");
                    JSONObject source = task.articles.getJSONObject(position).getJSONObject("source");
                    String name = source.getString("name");
                    String author = task.articles.getJSONObject(position).getString("author");
                    if (author.equals("null")) {
                        author = "Inconnu";
                    }
                    String date = task.articles.getJSONObject(position).getString("publishedAt");
                    String image = task.articles.getJSONObject(position).getString("urlToImage");
                    String content = task.articles.getJSONObject(position).getString("content");
                    if (content.equals("null")) {
                        content = "Aucun résumé disponible";
                    }
                    String url = task.articles.getJSONObject(position).getString("url");

                    Intent activity3 = new Intent(Activity2.this, Activity3.class);
                    activity3.putExtra("title", title);
                    activity3.putExtra("name", name);
                    activity3.putExtra("author", author);
                    activity3.putExtra("content", content);
                    activity3.putExtra("image", image);
                    activity3.putExtra("url", url);
                    try {
                        activity3.putExtra("date", Iso8601.reformat(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    startActivity(activity3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
