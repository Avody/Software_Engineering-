package com.example.shareytrips.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

import com.example.shareytrips.Post;
import com.example.shareytrips.R;
import com.google.firebase.auth.*;

import java.util.List;

public class PostAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Post> mPosts;
    private Activity mActivity;
    private boolean isFragment;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, ArrayList<Post> mPosts, Activity mActivity) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.mActivity = mActivity;
    }

    public PostAdapter(Context mContext, ArrayList<Post> mPosts, Boolean isFragment) {
        this.mContext = mContext;
        this.mPosts = mPosts;
        this.isFragment= isFragment;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new ViewHolder(view, viewType);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCity,mUsername,mShort_desc;
        public RatingBar mRatingBar;
        public Button mCheckMore;
        private CardView cardView;

        public ViewHolder(View itemView, int viewType) {
            super(itemView);
            // Find all views ids
            cardView = (CardView) itemView.findViewById(R.id.card_view_top);
            mCity = itemView.findViewById(R.id.city);
            mUsername = itemView.findViewById(R.id.username);
            mShort_desc = itemView.findViewById(R.id.shortDescription);
            mRatingBar = itemView.findViewById(R.id.rating_stars);
            mCheckMore = itemView.findViewById(R.id.button1);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder mainHolder, int position) {
        ViewHolder holder = (ViewHolder) mainHolder;

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

}
