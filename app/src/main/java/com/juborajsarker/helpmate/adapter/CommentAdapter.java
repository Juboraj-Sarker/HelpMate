package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.CommentModel;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder>{

    public Context context;
    public Activity activity;
    List<CommentModel> commentList;
   // CommentAdapter adapter;
    RecyclerView commentRV;



    public class MyViewHolder extends RecyclerView.ViewHolder{

        CardView fullChildCV;
        ImageView userIV;
        TextView userNameTV, commentTV;

        public MyViewHolder(View view) {

            super(view);

            fullChildCV = (CardView) view.findViewById(R.id.full_child_CV);
            userIV = (ImageView) view.findViewById(R.id.user_IV);
            userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
            commentTV = (TextView) view.findViewById(R.id.comment_TV);

        }
    }


    public CommentAdapter(Context context, Activity activity, List<CommentModel> commentList, RecyclerView commentRV) {

        this.context = context;
        this.activity = activity;
        this.commentList = commentList;
       // this.adapter = adapter;
        this.commentRV = commentRV;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_comment, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        CommentModel comment = commentList.get(position);

        holder.commentTV.setText(comment.getCommentText());
        holder.userNameTV.setText(comment.getUserName());
    }

    @Override
    public int getItemCount() {

        return commentList.size();
    }

}
