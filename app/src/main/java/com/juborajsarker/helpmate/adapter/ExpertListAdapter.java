package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.ExpertDetailsActivity;
import com.juborajsarker.helpmate.model.UserModel;

import java.util.List;

/**
 * Created by jubor on 11/14/2017.
 */

public class ExpertListAdapter extends RecyclerView.Adapter<ExpertListAdapter.MyViewHolder>{

    Activity activity;
    Context context;
    List<UserModel> expertList;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView userNameTV, expertCityTV, totalNumberOfReviewTV;
        public ImageView userIV;
        public RatingBar ratingBar;
        CardView fullChildCV;

        public MyViewHolder(View view){
            super(view);

            userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
            expertCityTV = (TextView) view.findViewById(R.id.expert_city_TV);
            totalNumberOfReviewTV = (TextView) view.findViewById(R.id.total_rating_TV);
            userIV = (ImageView) view.findViewById(R.id.user_IV);
            ratingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            fullChildCV = (CardView) view.findViewById(R.id.full_child_CV);



        }
    }


    public ExpertListAdapter(Activity activity, Context context, List<UserModel> expertList) {
        this.activity = activity;
        this.context = context;
        this.expertList = expertList;


    }




    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_expert, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final UserModel user = expertList.get(position);

        holder.userNameTV.setText(user.getUserName());
        holder.expertCityTV.setText(user.getCity());

        final String uid = user.getUserID();

        holder.fullChildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ExpertDetailsActivity.class);
                intent.putExtra("toUserId", uid);
                intent.putExtra("fullName", user.getFullName());
                intent.putExtra("userName", user.getUserName());
                intent.putExtra("phone", user.getPhone());
                intent.putExtra("email", user.getEmail());
                intent.putExtra("city", user.getCity());
                intent.putExtra("country", user.getCountry());

                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return expertList.size();
    }
}
