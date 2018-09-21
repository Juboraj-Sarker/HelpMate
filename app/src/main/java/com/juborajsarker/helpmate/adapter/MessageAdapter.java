package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.MessageModel;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Activity activity;
    private Context context;
    List<MessageModel> messageList;
    String uid = FirebaseAuth.getInstance().getUid();


    public class MyViewHolder extends RecyclerView.ViewHolder{

        LinearLayout senderFulLChildLAYOUT, receiverFullChildLAYOUT;
        TextView senderMessageTextTV, senderMessageDateTimeTV;
        TextView receiverMessageTextTV, receiverMessageDateTimeTV;


        public MyViewHolder(View view) {
            super(view);

            senderFulLChildLAYOUT = (LinearLayout) view.findViewById(R.id.sender_full_child_LAYOUT);
            receiverFullChildLAYOUT = (LinearLayout) view.findViewById(R.id.receiver_full_child_LAYOUT);

            senderMessageTextTV = (TextView) view.findViewById(R.id.sender_message_text_TV);
            senderMessageDateTimeTV = (TextView) view.findViewById(R.id.sender_date_time_TV);

            receiverMessageTextTV = (TextView) view.findViewById(R.id.receiver_message_text_TV);
            receiverMessageDateTimeTV = (TextView) view.findViewById(R.id.receiver_date_time_TV);


        }
    }

    public MessageAdapter(Activity activity, Context context, List<MessageModel> messageList) {
        this.activity = activity;
        this.context = context;
        this.messageList = messageList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_single_message, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MessageModel message = messageList.get(position);



        if (uid.equals(message.getSendFrom())){

         holder.receiverFullChildLAYOUT.setVisibility(View.GONE);
         holder.senderMessageTextTV.setText(message.getMainMessageText());
         holder.senderMessageDateTimeTV.setText(message.getDate() + " " + message.getTime());

        }else {

            holder.senderFulLChildLAYOUT.setVisibility(View.GONE);
            holder.receiverMessageTextTV.setText(message.getMainMessageText());
            holder.receiverMessageDateTimeTV.setText(message.getDate() + " " + message.getTime());

        }





    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}
