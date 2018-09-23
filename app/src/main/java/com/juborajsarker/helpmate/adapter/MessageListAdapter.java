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
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.ChattingActivity;
import com.juborajsarker.helpmate.model.LastChat;

import java.util.List;

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    private List<LastChat> lastChatList;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView userNameTV, messageTV, messageDateTV, messageTimeTV;
        public ImageView userIV;
        public CardView fullChildCV;

        public MyViewHolder(View view) {

            super(view);

            userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
            messageTV = (TextView) view.findViewById(R.id.messageTV);
            messageDateTV = (TextView) view.findViewById(R.id.message_date_TV);
            messageTimeTV = (TextView) view.findViewById(R.id.message_time_TV);
            userIV = (ImageView) view.findViewById(R.id.user_IV);
            fullChildCV = (CardView) view.findViewById(R.id.full_child_CV);

        }
    }

    public MessageListAdapter(Activity activity, Context context, List<LastChat> lastChatList) {
        this.activity = activity;
        this.context = context;
        this.lastChatList = lastChatList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_message_list, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        final LastChat lastChat = lastChatList.get(position);

        holder.userNameTV.setText(lastChat.getUserName());
        holder.messageTV.setText(lastChat.getLastMessage());
        holder.messageDateTV.setText(lastChat.getLastMessageDate());
        holder.messageTimeTV.setText(lastChat.getLastMessageTime());


        holder.fullChildCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChattingActivity.class);
                intent.putExtra("toUserId", lastChat.getUid());
                intent.putExtra("fullName", lastChat.getUserName());
                context.startActivity(intent);
            }
        });

        holder.userIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ChattingActivity.class);
                intent.putExtra("toUserId", lastChat.getUid());
                intent.putExtra("fullName", lastChat.getUserName());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {

        return lastChatList.size();
    }

}
