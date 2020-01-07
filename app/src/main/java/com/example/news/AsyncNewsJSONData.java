package com.example.news;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class AsyncNewsJSONData extends AsyncTask<String, Void, JSONObject> {
    JSONArray articles;
    private AppCompatActivity myActivity;

    AsyncNewsJSONData(AppCompatActivity activity) {
        myActivity = activity;
    }

    @Override
    protected JSONObject doInBackground(String... strings) {

        URL url;
        HttpURLConnection urlConnection = null;
        String result = null;
        try {
            url = new URL(strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream

            result = readStream(in); // Read stream
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }

        JSONObject json = null;
        try {
            if (result != null) {
                json = new JSONObject(result);
            } else {
                throw new JSONException("Bah en fait result c'est null");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json; // returns the result
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(is), 1000);
        for (String line = r.readLine(); line != null; line = r.readLine()) {
            sb.append(line);
        }
        is.close();

        return sb.toString();
    }

    @Override
    protected void onPostExecute(JSONObject result) {
        ListView list = myActivity.findViewById(R.id.articlesList);
        ArrayAdapter<String> fieldsAdapter = new ArrayAdapter<>(
                list.getContext(),
                R.layout.article,
                R.id.textView
        );
        list.setAdapter(fieldsAdapter);
        try {
            articles = result.getJSONArray("articles");
            for (int i = 0; i < articles.length(); i++) {
                try {
                    JSONObject article = articles.getJSONObject(i);
                    String title = article.getString("title");
                    JSONObject source = article.getJSONObject("source");
                    String name = source.getString("name");
                    String date = article.getString("publishedAt");
                    try {
                        date = Iso8601.reformat(date);
                        fieldsAdapter.add("\n" +
                                "Titre : " + title + "\n\n" +
                                "Source : " + name + "\n\n" +
                                "Date : " + date + "\n");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
