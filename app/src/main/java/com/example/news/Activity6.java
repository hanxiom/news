package com.example.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import static com.example.news.Contract.Entry.COLUMN_NAME_TITLE;
import static com.example.news.Contract.Entry.TABLE_NAME;

public class Activity6 extends AppCompatActivity {
    String title;
    String source;
    String author;
    String date;
    String image;
    String content;
    String url;

    Snackbar mySnackbar;

    @Override
    public boolean onSupportNavigateUp() {
        // Enables the back button behaviour
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Set activity's title
        getSupportActionBar().setTitle("");
        // Add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_no_fav, menu);
        return true;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        TextView textView1 = findViewById(R.id.textView30);
        TextView textView2 = findViewById(R.id.textView31);
        TextView textView3 = findViewById(R.id.textView32);
        TextView textView4 = findViewById(R.id.textView33);
        TextView textView5 = findViewById(R.id.textView34);
        ImageView imageView = findViewById(R.id.imageView);
        Button lire = findViewById(R.id.lire);

        Intent intent = getIntent();

        title = intent.getStringExtra("title");
        source = intent.getStringExtra("name");
        author = intent.getStringExtra("author");
        date = intent.getStringExtra("date");
        image = intent.getStringExtra("image");
        content = intent.getStringExtra("content");
        url = intent.getStringExtra("url");

        textView1.setText("Titre : " + title + "\n");
        textView2.setText("Source : " + source + "\n");
        textView3.setText("Auteur : " + author + "\n");
        textView4.setText("Date de publication : " + date + "\n");
        //Loading image using Picasso
        Picasso.get().load(image).into(imageView);
        textView5.setText("Résumé : " + content + "\n");

        // Ouvrir l'article sur Internet
        lire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        Button notFavAnymore = findViewById(R.id.favoris);
        notFavAnymore.setText("Retirer des favoris");

        // Retirer l'article des favoris
        notFavAnymore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = ((NewsApp) getApplication()).dbHelper.getWritableDatabase();

                // Define 'where' part of query.
                String selection = COLUMN_NAME_TITLE + " = ?";
                // Specify arguments in placeholder order.
                String[] selectionArgs = {title};
                // Issue SQL statement.
                int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
                if (deletedRows > 0) {
                    mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "L'article a été retiré de vos favori", Snackbar.LENGTH_LONG);
                } else {
                    mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Une erreur est survenue !", Snackbar.LENGTH_LONG);
                }
                mySnackbar.show();
            }
        });
    }

    public void onComposeAction(MenuItem mi) {
        if (mi.getItemId() == R.id.infos_item) {
            Intent activity4 = new Intent(Activity6.this, Activity4.class);
            startActivity(activity4);
        } else {
            throw new RuntimeException("Aie !");
        }
    }
}
