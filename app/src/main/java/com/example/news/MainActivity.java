package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    protected String country;
    protected String category;
    protected String sources;
    protected String keywords;
    private Spinner spinner1;
    private Spinner spinner2;
    private EditText sourcesField;
    private EditText keywordsField;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((NewsApp) getApplication()).dbHelper = new DbHelper(this);

        Button lancer_la_recherche = findViewById(R.id.lancer_la_recherche);
        Button infos = findViewById(R.id.infos);
        Button favBtn = findViewById(R.id.favoris);
        spinner1 = findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(this);
        spinner2 = findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        sourcesField = findViewById(R.id.editText1);
        keywordsField = findViewById(R.id.editText2);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.countries, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter1 to the spinner
        spinner1.setAdapter(adapter1);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.categories, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter1 to the spinner
        spinner2.setAdapter(adapter2);

        if (!isOnline()) {
            Toast.makeText(this, "Veuillez activer votre connexion Internet", Toast.LENGTH_LONG).show();
        }

        lancer_la_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOnline()) {
                    Toast.makeText(v.getContext(), "Veuillez activer votre connexion Internet", Toast.LENGTH_LONG).show();
                } else {
                    sources = sourcesField.getText().toString();
                    keywords = keywordsField.getText().toString();

                    Intent activity2 = new Intent(MainActivity.this, Activity2.class);
                    activity2.putExtra("country", country);
                    activity2.putExtra("category", category);
                    activity2.putExtra("sources", sources);
                    activity2.putExtra("keywords", keywords);
                    startActivity(activity2);
                }

            }
        });

        infos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity4 = new Intent(MainActivity.this, Activity4.class);
                startActivity(activity4);
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent activity5 = new Intent(MainActivity.this, Activity5.class);
                startActivity(activity5);
            }
        });

        String langue = this.getResources().getConfiguration().locale.getDisplayLanguage();


        String langue2 = langue.substring(0, 2);

        for (int i = 1; i <= 53; i++) {
            if (langue2.equals(spinner1.getItemAtPosition(i).toString())) {
                spinner1.setSelection(i);
                break;
            }
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        if (parent == spinner1) {
            country = parent.getItemAtPosition(pos).toString();
        } else if (parent == spinner2) {
            category = parent.getItemAtPosition(pos).toString();
        } else {
            try {
                throw new Exception("Duck!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


//    public boolean isInternetAvailable() {
//        try {
//            InetAddress ipAddr = InetAddress.getByName("google.com");
//            //You can replace it with your name
//            return !ipAddr.equals("");
//
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        }
        catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }

        return false;
    }


}
