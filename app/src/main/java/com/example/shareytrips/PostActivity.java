package com.example.shareytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import java.util.ArrayList;

public class PostActivity extends AppCompatActivity {
    //o pinakas autos prepei na pernei tis polis apo to firebase
    private String[] cities = new String[]{
            "Patras","Athens","Paris","Madrid","Manchester","London"
    };
    private String[] arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        AutoCompleteTextView city = findViewById(R.id.enterCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,cities);
        city.setAdapter(adapter);


    }
}