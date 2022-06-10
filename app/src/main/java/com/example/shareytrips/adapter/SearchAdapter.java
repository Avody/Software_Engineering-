package com.example.shareytrips.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareytrips.Post;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.shareytrips.R;


import java.util.HashMap;
import java.util.List;

public class SearchAdapter extends  RecyclerView.Adapter<SearchAdapter.Viewholder> {
    private Context mContext;
    private List<Post> mPosts;

    private FirebaseUser firebaseUser;

    public SearchAdapter(Context mContext, List<Post> mPosts) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post, parent, false);
        return new SearchAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.Viewholder holder, int position) {
        final Post post = mPosts.get(position);

        FirebaseDatabase.getInstance().getReference().child("Posts").child(post.getUsername()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Post post = snapshot.getValue(Post.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{
        public TextView mCity,mUsername,mShort_desc;
        public RatingBar mRatingBar;
        public Button mCheckMore;
        private CardView cardView;

        public Viewholder(View itemView) {
            super(itemView);
            // Find all views ids
            cardView = (CardView) itemView.findViewById(R.id.card_view_top);
            mCity = itemView.findViewById(R.id.city);
            mUsername = itemView.findViewById(R.id.username);
            mShort_desc = itemView.findViewById(R.id.shortDescription);
            mRatingBar = itemView.findViewById(R.id.rating_stars);
            mCheckMore = itemView.findViewById(R.id.check_more);
        }
    }
}
