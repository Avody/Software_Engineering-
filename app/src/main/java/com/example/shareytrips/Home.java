package com.example.shareytrips;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareytrips.adapter.PostAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
public class Home  extends AppCompatActivity{
    private static final String TAG = "HomeActivity";
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    private Context mContext;
    private Activity mActivity;
    private ArrayList<Post> postsList;
    private PostAdapter postAdapter = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivity = Home.this;
        mContext = getApplicationContext();
        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2, GridLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);
        postsList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Images");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                postsList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Post imagemodel = dataSnapshot.getValue(Post.class);
                    postsList.add(imagemodel);
                }
                postAdapter = new PostAdapter(mContext, (ArrayList<Post>) postsList,mActivity);
                recyclerView.setAdapter(postAdapter);
                postAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home.this,"Error:" + error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
}
