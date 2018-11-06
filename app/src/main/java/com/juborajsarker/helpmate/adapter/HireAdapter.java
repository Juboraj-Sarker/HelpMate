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
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.GiveRatingActivity;
import com.juborajsarker.helpmate.model.HireModel;

import java.util.List;

public class HireAdapter extends RecyclerView.Adapter<HireAdapter.MyViewHolder> {

    public Activity activity;
    public Context context;
    public List<HireModel> hireModelList;



    public class MyViewHolder extends RecyclerView.ViewHolder{

       public TextView nameTV;
       public CardView fullChildCV;

        public MyViewHolder(View view) {
            super(view);

            nameTV = (TextView) view.findViewById(R.id.expert_name_TV);
            fullChildCV = (CardView) view.findViewById(R.id.full_child_CV);
        }
    }


    public HireAdapter(Activity activity, Context context, List<HireModel> hireModelList) {
        this.activity = activity;
        this.context = context;
        this.hireModelList = hireModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_helper, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final HireModel hireModel = hireModelList.get(position);

        holder.nameTV.setText(hireModel.getHireExpertName());

        holder.fullChildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GiveRatingActivity.class);
                intent.putExtra("expertUid", hireModel.getHireExpertUserId());
                intent.putExtra("expertName", hireModel.getHireExpertName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return hireModelList.size();
    }
}
