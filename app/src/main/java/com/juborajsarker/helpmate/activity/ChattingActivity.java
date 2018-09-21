package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.MessageAdapter;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.java_class.GridSpacingItemDecoration;
import com.juborajsarker.helpmate.model.MessageModel;
import com.juborajsarker.helpmate.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {

    EditText messageET;
    ImageView sendMessageIV;
    RecyclerView chatRV;

    DatabaseReference reference;
    List<MessageModel> messageList = new ArrayList<>();

    String messageText;
    String uid, userName, userFullName, sendFrom, sendTo, date, time, messageID, receiverName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        Intent intent = getIntent();
        sendTo = intent.getStringExtra("toUserId");
        receiverName = intent.getStringExtra("fullName");
        setTitle(receiverName);
        init();
        getMessage();
        uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Chat/" + uid);



    }



    private void init() {

        messageET = (EditText) findViewById(R.id.message_ET);
        sendMessageIV = (ImageView) findViewById(R.id.send_message_IV);
        chatRV = (RecyclerView) findViewById(R.id.chat_RV);

        sendMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkValidity()){

                    messageText = messageET.getText().toString();
                    sendFrom = uid;
                    date = new DateTimeConverter().getCurrentDate();
                    time = new DateTimeConverter().getCurrentTime();
                    messageID = reference.push().getKey();
                    DatabaseReference userReference = FirebaseDatabase.getInstance().getReference("User/All");
                    getUserInfo(userReference);








                }
            }
        });
    }

    private void getUserInfo(DatabaseReference userReference) {

        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    UserModel userModel = snapshot.getValue(UserModel.class);

                    if (userModel.getUserID().equals(uid)){

                         userFullName = userModel.getFullName();
                         userName = userModel.getUserName();


                        MessageModel messageModel = new MessageModel();
                        messageModel.setMessageID(messageID);
                        messageModel.setSendFrom(sendFrom);
                        messageModel.setSendTo(sendTo);
                        messageModel.setUserName(userName);
                        messageModel.setUserFullName(userFullName);
                        messageModel.setMainMessageText(messageText);
                        messageModel.setDate(date);
                        messageModel.setTime(time);

                        DatabaseReference senderReference = FirebaseDatabase.getInstance().getReference
                                ("Chat/"+uid+"/" + sendTo);
                        senderReference.child(messageID).setValue(messageModel);


                      //  messageModel.setSendTo(sendFrom);
                      //  messageModel.setSendFrom(sendTo);
                        DatabaseReference receiverReference = FirebaseDatabase.getInstance().getReference
                                ("Chat/"+sendTo+"/" + sendFrom);
                        receiverReference.child(messageID).setValue(messageModel);

                        messageET.setText("");
                        getMessage();


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



    private void getMessage() {

        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("Chat/" + uid+ "/" + sendTo);
        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                messageList.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    MessageModel message = snapshot.getValue(MessageModel.class);
                    messageList.add(message);



                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        MessageAdapter messageAdapter = new MessageAdapter(ChattingActivity.this, ChattingActivity.this, messageList);
        RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(ChattingActivity.this, 1);
        chatRV.setLayoutManager(layoutManagerBeforeMeal);
        chatRV.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(2), true));
        chatRV.setItemAnimator(new DefaultItemAnimator());

        chatRV.setAdapter(messageAdapter);
        messageAdapter.notifyDataSetChanged();

    }



    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public boolean checkValidity(){

        if (messageET.getText().toString().equals("")){

            messageET.setError("Please enter a message");
            return false;

        }else if (messageET.getText().toString().length()>0 && Character.isWhitespace(messageET.getText().toString().charAt(0))){

            messageET.setError("Message cannot start with a space");
            return false;

        }else {

            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{

                super.onBackPressed();

            }default:{

                return super.onOptionsItemSelected(item);
            }



        }

    }


}
