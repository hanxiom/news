package com.example.news;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

import java.io.InputStream;
import java.net.URL;

public class Activity3 extends AppCompatActivity {

    String title;
    String source;
    String author;
    String date;
    String image;
    String content;
    String url;

    Snackbar mySnackbar;

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
        Button favoris = findViewById(R.id.favoris);

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

        favoris.setText("Mettre en favori");

        // Ouvrir l'article sur Internet
        lire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // Ajouter l'article aux favoris
        favoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = ((NewsApp) getApplication()).dbHelper.insertRow(title, source, author, date, content, url);

                if (success) {
                    mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "L'article a été enregistré dans vos favori", Snackbar.LENGTH_LONG);
                } else {
                    mySnackbar = Snackbar.make(findViewById(R.id.myCoordinatorLayout), "Une erreur est survenue !", Snackbar.LENGTH_LONG);
                }
                mySnackbar.show();
            }
        });
    }
}
