package com.example.shareytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;


import java.util.Calendar;
import java.util.HashMap;

public class PostActivity extends AppCompatActivity {


    private AutoCompleteTextView mCompany,mCity;
    private EditText mDate1,mDate2,smallDesc,bigDesc,cost;
    private RatingBar ratingStars;
    private ImageView close;
    private TextView post_button;

    private String[] arrayAdapter;
    private int mDay1,mMonth1,mYear1,mDay2,mMonth2,mYear2,rating;
    private String company_choice;

    private String[] cities = new String[]{"Patras","Athens","Paris","Madrid","Manchester","London"};
    private String[] companions = {"Couple","Family","Co workers","Groub of guys","Group of girls"};

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        smallDesc = findViewById(R.id.smallDesc);
        bigDesc = findViewById(R.id.bigDesc);
        cost = findViewById(R.id.cost);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Posts");

        //x button
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PostActivity.this,MainActivity.class));
                finish();
            }
        });

        //for the select the city you visited field
        mCity = findViewById(R.id.city);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,cities);
        mCity.setAdapter(adapter);

        mDate1 = findViewById(R.id.date1);
        mDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDay1 = Cal.get((Calendar.DATE));
                mMonth1 = Cal.get((Calendar.MONTH));
                mYear1 = Cal.get((Calendar.YEAR));
                DatePickerDialog datePickerDialog = new DatePickerDialog(PostActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                            mDate1.setText(date+"-"+month+"-"+year);
                    }
                },mYear1,mMonth1,mDay1);
                datePickerDialog.show();

            }
        });

        mDate2 = findViewById(R.id.date2);
        mDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar Cal = Calendar.getInstance();
                mDay2 = Cal.get((Calendar.DATE));
                mMonth2 = Cal.get((Calendar.MONTH));
                mYear2 = Cal.get((Calendar.YEAR));
                DatePickerDialog datePickerDialog2 = new DatePickerDialog(PostActivity.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        mDate2.setText(date+"-"+month+"-"+year);
                    }
                },mYear2,mMonth2,mDay1);
                datePickerDialog2.show();

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
        mCompany = findViewById(R.id.company);
        ArrayAdapter<String> adapterCompanions = new ArrayAdapter<String>(this,
                R.layout.activity_companions,companions);
        mCompany.setAdapter(adapterCompanions);
        mCompany.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                company_choice = parent.getItemAtPosition(i).toString();

            }
        });
        //post button
        post_button = findViewById(R.id.post);
        String postId = databaseReference.push().getKey();
        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        HashMap<String , Object> map = new HashMap<>();
                        Post post1 = new Post(mCity.getText().toString(),
                                company_choice,
                                cost.getText().toString(),
                                smallDesc.getText().toString(),
                                mDate1.getText().toString(),
                                mDate2.getText().toString(),
                                bigDesc.getText().toString(),
                                rating,
                                FirebaseAuth.getInstance().getCurrentUser().getDisplayName()
                        );
                        map.put("city" , post1.getCity());
                        map.put("travel companions" , post1.getCompany_choice());
                        map.put("cost" , post1.getCost());
                        map.put("short description" , post1.getSmallDesc());
                        map.put("Arriving date" , post1.getDate1());
                        map.put("Leaving date" , post1.getDate2());
                        map.put("Long description" , post1.getBigDesc());
                        map.put("rating" , post1.getRating());
                        map.put("publisher" , post1.getUsername());
                        databaseReference.child(postId).setValue(map);
                        Toast.makeText(PostActivity.this, "post uploaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PostActivity.this, "Fail to upload the post " + error, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}