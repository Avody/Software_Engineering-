package com.example.shareytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class PostActivity extends AppCompatActivity {
    //o pinakas autos prepei na pernei tis polis apo to firebase
    private String[] cities = new String[]{
            "Patras","Athens","Paris","Madrid","Manchester","London"
    };
    //edw exw tis epiloges gia ta travel companions
    String[] companions = {"Couple","Family","Co workers","Groub of guys","Group of girls"};
    AutoCompleteTextView company;
    String company_choice;

    private String[] arrayAdapter;
    EditText date1,date2;
    private int mDate1,mMonth1,mYear1,mDate2,mMonth2,mYear2;
    RatingBar ratingStars;
    private int rating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        //for the select the city you visited field
        AutoCompleteTextView city = findViewById(R.id.enterCity);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,cities);
        city.setAdapter(adapter);
        //for the dates
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDate1 = Cal.get((Calendar.DATE));
                mMonth1 = Cal.get((Calendar.MONTH));
                mYear1 = Cal.get((Calendar.YEAR));
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                            date1.setText(date+"-"+month+"-"+year);
                    }
                },mYear1,mMonth1,mDate1);
                datePickerDialog.show();

            }
        });
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDate2 = Cal.get((Calendar.DATE));
                mMonth2 = Cal.get((Calendar.MONTH));
                mYear2 = Cal.get((Calendar.YEAR));
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        date2.setText(date+"-"+month+"-"+year);
                    }
                },mYear2,mMonth2,mDate2);
                datePickerDialog.show();

            }
        });
        //rating Stars
        ratingStars = findViewById(R.id.ratingBar);
        ratingStars.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (int) v;
            }
        });


        //travel companions
        company = findViewById(R.id.company);
        ArrayAdapter<String> adapterCompanions = new ArrayAdapter<String>(this,
                R.layout.activity_companions,companions);
        company.setAdapter(adapterCompanions);
        company.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                company_choice = parent.getItemAtPosition(i).toString();

            }
        });


    }
}