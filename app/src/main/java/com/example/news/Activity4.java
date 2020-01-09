package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class Activity4 extends AppCompatActivity {

    @Override
    public boolean onSupportNavigateUp() {
        // Enables the back button behaviour
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set activity's title
        getSupportActionBar().setTitle("A propos");
        // Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_info, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity4);
    }

    public void onComposeAction(MenuItem mi) {
        if (mi.getItemId() == R.id.fav_item) {
            Intent activity5 = new Intent(Activity4.this, Activity5.class);
            startActivity(activity5);
        } else {
            throw new RuntimeException("Aie !");
        }
    }
}
