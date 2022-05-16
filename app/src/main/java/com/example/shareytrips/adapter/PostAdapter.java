package com.example.shareytrips.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.shareytrips.Post;
import com.example.shareytrips.R;
import com.google.firebase.auth.*;

import java.util.List;

public class PostAdapter extends  RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context mContext;
    private List<Post> mPosts;
    private boolean isFargment;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPosts, boolean isFargment) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.isFargment = isFargment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item , parent , false);
        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.mCity.setText(post.getCity());
        holder.mRatingBar.setRating(post.getRating());
        holder.mUsername.setText(post.getUsername());
        holder.mShort_desc.setText(post.getSmallDesc());
    }

    @Override
    public int getItemCount() {
        return mPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mCity,mUsername,mShort_desc;
        public RatingBar mRatingBar;
        public Button mCheckMore;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mCity = itemView.findViewById(R.id.city);
            mUsername = itemView.findViewById(R.id.username);
            mShort_desc = itemView.findViewById(R.id.shortDescription);
            mRatingBar = itemView.findViewById(R.id.rating_stars);
            mCheckMore = itemView.findViewById(R.id.button1);

        }
    }
}
