package com.juborajsarker.helpmate.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.MessageListAdapter;
import com.juborajsarker.helpmate.java_class.GridSpacingItemDecoration;
import com.juborajsarker.helpmate.model.LastChat;

import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment {

    View view;
    RecyclerView chatRV;
    String uid;
    DatabaseReference reference;
    List<LastChat> lastChatList = new ArrayList<>();



    public InboxFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_inbox, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setTitle("Message List");


        uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("LastChat/" + uid);

        init();
        loadData();



        return view;
    }



    private void init() {

        chatRV = (RecyclerView) view.findViewById(R.id.chat_RV);
    }


    private void loadData() {

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                lastChatList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    LastChat lastChat = snapshot.getValue(LastChat.class);
                    lastChatList.add(lastChat);

                }

                MessageListAdapter adapter = new MessageListAdapter(getActivity(), getContext(), lastChatList);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                chatRV.setLayoutManager(layoutManager);
                chatRV.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                chatRV.setItemAnimator(new DefaultItemAnimator());

                chatRV.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:{

                Toast.makeText(getContext(), "On BackPressed", Toast.LENGTH_SHORT).show();

            }default:{

                return super.onOptionsItemSelected(item);
            }



        }

    }


    @Override
    public void onResume() {
        super.onResume();

        loadData();
    }
}
