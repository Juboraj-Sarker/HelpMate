package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.ReviewModel;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    public Activity activity;
    public Context context;
    public List<ReviewModel> reviewModelList;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public RatingBar ratingBar;
        public TextView reviewTV, givenByTV;

        public MyViewHolder(View view) {
            super(view);

            ratingBar = (RatingBar) view.findViewById(R.id.rating_BAR);
            reviewTV = (TextView) view.findViewById(R.id.rating_TV);
            givenByTV = (TextView) view.findViewById(R.id.given_by_TV);

        }
    }


    public ReviewAdapter(Activity activity, Context context, List<ReviewModel> reviewModelList) {
        this.activity = activity;
        this.context = context;
        this.reviewModelList = reviewModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_review, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        ReviewModel reviewModel = reviewModelList.get(position);

        holder.ratingBar.setRating(Float.parseFloat(reviewModel.getReviewRete()));
        holder.reviewTV.setText(reviewModel.getReviewText());
        holder.givenByTV.setText(reviewModel.getReviewGivenByName());

    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }
}
