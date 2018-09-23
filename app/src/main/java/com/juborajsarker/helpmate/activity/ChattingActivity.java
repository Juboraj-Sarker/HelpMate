package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.MessageAdapter;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.java_class.GridSpacingItemDecoration;
import com.juborajsarker.helpmate.model.LastChat;
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
    MessageAdapter messageAdapter;

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

        uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Chat/" + uid);

        getMessage();

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

                        DatabaseReference receiverReference = FirebaseDatabase.getInstance().getReference
                                ("Chat/"+sendTo+"/" + sendFrom);
                        receiverReference.child(messageID).setValue(messageModel);

                        messageET.setText("");
                        storeMessageForReceiver(messageModel);



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
        messageRef.keepSynced(true);


        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                messageList.clear();
                chatRV.removeAllViews();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    MessageModel message = snapshot.getValue(MessageModel.class);
                    messageList.add(message);



                }



                 messageAdapter = new MessageAdapter(ChattingActivity.this,
                        ChattingActivity.this, messageList);

                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(ChattingActivity.this, 1);
                chatRV.setLayoutManager(layoutManager);
                chatRV.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                chatRV.setItemAnimator(new DefaultItemAnimator());

                chatRV.setAdapter(messageAdapter);
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




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


    @Override
    protected void onPause() {
        super.onPause();
        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference("Chat/" + uid+ "/" + sendTo);
        messageRef.keepSynced(true);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Chat/" + uid);
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {




            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        storeLastMessage();

    }

    private void storeLastMessage() {

        if (messageList.size()>0){

            String uid = FirebaseAuth.getInstance().getUid();
            String userName = receiverName;
            String lastMessageId = messageList.get(messageList.size()-1).getMessageID();
            String lastMessageText = messageList.get(messageList.size()-1).getMainMessageText();
            String lastMessageDate = messageList.get(messageList.size()-1).getDate();
            String lastMessageTime = messageList.get(messageList.size()-1).getTime();

            LastChat lastChat = new LastChat();
            lastChat.setUid(sendTo);
            lastChat.setUserName(userName);
            lastChat.setLastMessageId(lastMessageId);
            lastChat.setLastMessage(lastMessageText);
            lastChat.setLastMessageDate(lastMessageDate);
            lastChat.setLastMessageTime(lastMessageTime);


            DatabaseReference lastRef = FirebaseDatabase.getInstance().getReference("LastChat/"+uid+"/"+sendTo);
            lastRef.setValue(lastChat);
        }
    }


    public void storeMessageForReceiver(MessageModel messageModel){

        DatabaseReference receiverReference = FirebaseDatabase.getInstance().getReference
                ("LastChat/"+sendTo+"/" + sendFrom);


        String uid = FirebaseAuth.getInstance().getUid();
        String lastMessageId = messageModel.getMessageID();
        String lastMessageText = messageModel.getMainMessageText();
        String lastMessageDate = messageModel.getDate();
        String lastMessageTime = messageModel.getTime();

        LastChat lastChat = new LastChat();
        lastChat.setUid(sendFrom);
        lastChat.setUserName(userName);
        lastChat.setLastMessageId(lastMessageId);
        lastChat.setLastMessage(lastMessageText);
        lastChat.setLastMessageDate(lastMessageDate);
        lastChat.setLastMessageTime(lastMessageTime);


        DatabaseReference lastRef = FirebaseDatabase.getInstance().getReference("LastChat/"+uid+"/"+sendTo);
        receiverReference.setValue(lastChat);


    }
}
