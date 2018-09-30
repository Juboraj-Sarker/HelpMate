package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.BeatModel;

import java.util.List;

public class BeatAdapter extends RecyclerView.Adapter<BeatAdapter.MyViewHolder>{

    public Activity activity;
    public Context context;
    public List<BeatModel> beatModelList;




    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView  postTV, priceTV, estimatedTimeTV;
        CardView fullChildCV;



        public MyViewHolder(View view) {
            super(view);

            postTV = (TextView) view.findViewById(R.id.post_TV);
            priceTV = (TextView) view.findViewById(R.id.beat_price_TV);
            estimatedTimeTV = (TextView) view.findViewById(R.id.estimated_time_TV);
            fullChildCV = (CardView) view.findViewById(R.id.full_child_CV);

        }
    }


    public BeatAdapter(Activity activity, Context context, List<BeatModel> beatModelList) {

        this.activity = activity;
        this.context = context;
        this.beatModelList = beatModelList;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_beat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BeatModel beat = beatModelList.get(position);


        holder.postTV.setText( beat.getExpertName()+" has beated on your post: " + beat.getPostText());
        holder.estimatedTimeTV.setText(beat.getEstimatedTime());
        holder.priceTV.setText(String.valueOf(beat.getBitPrice()) + "/=");

        holder.fullChildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    @Override
    public int getItemCount() {
        return beatModelList.size();
    }


}
