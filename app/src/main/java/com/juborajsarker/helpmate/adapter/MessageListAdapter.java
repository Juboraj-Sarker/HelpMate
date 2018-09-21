package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.MessageModel;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private List<MessageModel> messageList;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView userNameTV, messageTV, messageDateTV, messageTimeTV;
        public ImageView userIV;

        public MyViewHolder(View view) {

            super(view);

            userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
            messageTV = (TextView) view.findViewById(R.id.messageTV);
            messageDateTV = (TextView) view.findViewById(R.id.message_date_TV);
            messageTimeTV = (TextView) view.findViewById(R.id.message_time_TV);
            userIV = (ImageView) view.findViewById(R.id.user_IV);

        }
    }

    public MessageListAdapter(Activity activity, Context context, List<MessageModel> messageList) {
        this.activity = activity;
        this.context = context;
        this.messageList = messageList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        return messageList.size();
    }

}
