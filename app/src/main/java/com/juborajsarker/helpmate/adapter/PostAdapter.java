package com.juborajsarker.helpmate.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.BitActivity;
import com.juborajsarker.helpmate.activity.UserDetailsActivity;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.model.CommentModel;
import com.juborajsarker.helpmate.model.LikeModel;
import com.juborajsarker.helpmate.model.PostModel;
import com.juborajsarker.helpmate.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class
PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    public Context context;
    public Activity activity;
    public List<PostModel> postList;
    public PostAdapter adapter;
    public RecyclerView postRV;
    public boolean isExpert;
    public String uid;
    public String userName;

    public List<LikeModel> likeList = new ArrayList<>();


    List<CommentModel> commentList = new ArrayList<>();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    int counter = 0;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView postTV, userNameTV;
        ImageView postIV, userIV, likeIV;
        LinearLayout likeLAYOUT, commentLAYOUT, bitLAYOUT;
        CardView additionalCV;
        RecyclerView commentRV;
        EditText commentET;
        Button postCommentBTN;

        public MyViewHolder(View view) {

            super(view);

            postTV = (TextView) view.findViewById(R.id.post_TV);
            userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
            postIV = (ImageView) view.findViewById(R.id.post_IV);
            userIV = (ImageView) view.findViewById(R.id.user_IV);
            likeIV = (ImageView) view.findViewById(R.id.like_IV);
            likeLAYOUT = (LinearLayout) view.findViewById(R.id.like_LAYOUT);
            commentLAYOUT = (LinearLayout) view.findViewById(R.id.comment_LAYOUT);
            bitLAYOUT = (LinearLayout) view.findViewById(R.id.bit_LAYOUT);
            additionalCV = (CardView) view.findViewById(R.id.additional_CV);
            commentRV = (RecyclerView) view.findViewById(R.id.comment_RV);
            commentET = (EditText) view.findViewById(R.id.comment_ET);
            postCommentBTN = (Button) view.findViewById(R.id.comment_Post_BTN);

        }
    }


    public PostAdapter(Context context, Activity activity, List<PostModel> postList, PostAdapter adapter,
                       RecyclerView postRV, boolean isExpert, String uid, String userName) {

        this.context = context;
        this.activity = activity;
        this.postList = postList;
        this.adapter = adapter;
        this.postRV = postRV;
        this.isExpert = isExpert;
        this.uid = uid;
        this.userName = userName;



    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_post, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        final PostModel post = postList.get(position);

        String likeId = post.getLikeUid();

        if (likeId != null && likeId.contains(FirebaseAuth.getInstance().getUid())){

            holder.likeIV.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));
        }





        holder.postTV.setText(post.getPostText());
        holder.userNameTV.setText(post.getUserName());

        if (post.isHasImage()){

            FirebaseStorage storage = FirebaseStorage.getInstance();
            downloadAndSetImage(holder.postIV, storage, post);

        }else {

            holder.postIV.setVisibility(View.GONE);
        }

        if (isExpert){

            holder.bitLAYOUT.setVisibility(View.VISIBLE);

        }else {

            holder.bitLAYOUT.setVisibility(View.GONE);
        }


        holder.likeLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String postID = post.getPostID();
                String uid = FirebaseAuth.getInstance().getUid();
                String postText = post.getPostText();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Like/" + postID);
                String likeId = reference.push().getKey();

                LikeModel like = new LikeModel();
                like.setLikeID(likeId);
                like.setPostID(postID);
                like.setUid(uid);
                like.setPostText(postText);

                reference.child(uid).setValue(like);
                holder.likeIV.setColorFilter(activity.getResources().getColor(R.color.colorPrimary));

                String postLikeUid = post.getLikeUid();
                postLikeUid = postLikeUid + " " + uid;
                post.setLikeUid(postLikeUid);
                reference = FirebaseDatabase.getInstance().getReference("Post/All/" + postID);
                reference.setValue(post);

            }
        });


        holder.commentLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                counter++;
                if (counter % 2 == 1){

                    holder.additionalCV.setVisibility(View.VISIBLE);
                    showComment(holder.commentRV, post);

                }else if (counter % 2 == 0){

                    holder.additionalCV.setVisibility(View.GONE);
                }

            }
        });


        holder.postCommentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postComment(post, holder.commentET, holder.commentRV);

            }
        });


        holder.userIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showUserDetails(post);
            }
        });


        holder.userNameTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               showUserDetails(post);
            }
        });


        holder.bitLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, BitActivity.class);
                intent.putExtra("userName", post.getUserName());
                intent.putExtra("post", post.getPostText());
                intent.putExtra("uid", post.getUserID());
                intent.putExtra("postId", post.getPostID());
                context.startActivity(intent);

            }
        });


    }




    private void showUserDetails(PostModel post) {

        final String uid = post.getUserID();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User/All");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    UserModel userModel = snapshot.getValue(UserModel.class);

                    if (userModel.getUserID().equals(uid)){

                        String fullName = userModel.getFullName();
                        String userName = userModel.getUserName();
                        String phone = userModel.getPhone();
                        String email = userModel.getEmail();
                        String city = userModel.getCity();
                        String country = userModel.getCountry();

                        Intent intent = new Intent(context, UserDetailsActivity.class);
                        intent.putExtra("toUserId", uid);
                        intent.putExtra("fullName", fullName);
                        intent.putExtra("userName", userName);
                        intent.putExtra("phone", phone);
                        intent.putExtra("email", email);
                        intent.putExtra("city", city);
                        intent.putExtra("country", country);

                        context.startActivity(intent);

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }

    private void postComment(PostModel post, EditText commentEt, RecyclerView commentRV) {

       if (checkCommentValidity(commentEt)){


           String pushKey = databaseReference.push().getKey();
           databaseReference = FirebaseDatabase.getInstance()
                   .getReference("Comment/" + post.getPostID() +"/"+ pushKey);

           String commentText = commentEt.getText().toString();


           CommentModel comment = new CommentModel();
           comment.setPostID(post.getPostID());
           comment.setUserID(uid);
           comment.setUserImageURL("null");
           comment.setUserName(userName);
           comment.setCommentText(commentText);
           comment.setCommentDate(new DateTimeConverter().getCurrentDate());
           comment.setCommentTime(new DateTimeConverter().getCurrentTime());
           comment.setCommentID(pushKey);

           hideInput();
           commentEt.setText("");

           databaseReference.setValue(comment);
           showComment(commentRV, post);

       }else {

           hideInput();
       }


    }


    private void showComment(final RecyclerView commentRV, PostModel post) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                .getReference("Comment/" + post.getPostID());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()){

                    commentList.clear();

                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                        CommentModel comment = snapshot.getValue(CommentModel.class);
                        commentList.add(comment);
                    }

                    CommentAdapter commentAdapter = new CommentAdapter(context, activity, commentList, commentRV);
                    setRecyclerView(commentRV, commentAdapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(context, ""+ databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void setRecyclerView(RecyclerView commentRV, CommentAdapter adapter) {


        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(context, 1);
        commentRV.setLayoutManager(layoutManagerBeforeMeal);
        commentRV.setItemAnimator(new DefaultItemAnimator());

        adapter.notifyDataSetChanged();
        commentRV.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }




    private void downloadAndSetImage(final ImageView postIV, FirebaseStorage storage, PostModel post) {

        try {
            storage.getReference().child("Post/All/" + post.getPostID() + "/" + post.getPostID() + ".jpg").getDownloadUrl().
                    addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {


                        }

                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }

                    }).addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            try {

                                Glide.with(context)
                                        .load(uri)
                                        .skipMemoryCache(false)
                                        .into(postIV);

                                scaledBitmap(postIV);


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {

        return postList.size();
    }


    public boolean checkCommentValidity (EditText commentET){

        if (commentET.getText().toString().equals("")){

            commentET.setError("Please enter a comment");
            return false;

        }else if ((commentET.getText().toString().length() > 0
                && Character.isWhitespace(commentET.getText().toString().charAt(0)))){

            commentET.setError("Comment cannot start with a space");
            return false;

        }else {

            return true;
        }
    }



    private Bitmap scaledBitmap(ImageView imageView){

        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        Bitmap bMapScaled = Bitmap.createScaledBitmap(bitmap, 350, 190, true);
        imageView.setImageBitmap(bMapScaled);

        return bitmap;
    }

    private void hideInput() {

        InputMethodManager inputManager = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }
}
