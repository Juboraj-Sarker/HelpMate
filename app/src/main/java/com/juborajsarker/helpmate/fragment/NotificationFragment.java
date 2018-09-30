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
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.BeatAdapter;
import com.juborajsarker.helpmate.model.BeatModel;

import java.util.ArrayList;
import java.util.List;


public class NotificationFragment extends Fragment {

    View view;
    TextView messageTV;
    LinearLayout noNotificationLAYOUT;
    RecyclerView beatRV;
    List<BeatModel> beatList = new ArrayList<>();
    String uid = FirebaseAuth.getInstance().getUid();
    DatabaseReference reference;



    public NotificationFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_notification, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setTitle("Beat");

        init();
        loadData();


        return view;
    }



    private void init() {

        noNotificationLAYOUT = (LinearLayout) view.findViewById(R.id.no_notification_LAYOUT);
        beatRV = (RecyclerView) view.findViewById(R.id.beat_RV);
        messageTV = (TextView) view.findViewById(R.id.messageTV);


    }

    private void loadData() {

        reference = FirebaseDatabase.getInstance().getReference("Beat/All/" + uid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    BeatModel beatModel = snapshot.getValue(BeatModel.class);
                    beatList.add(beatModel);
                }

                if (beatList.size()>0){

                    noNotificationLAYOUT.setVisibility(View.GONE);
                    beatRV.setVisibility(View.VISIBLE);
                    BeatAdapter adapter = new BeatAdapter(getActivity(), getContext(), beatList);
                    RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(getContext(), 1);
                    beatRV.setLayoutManager(layoutManagerBeforeMeal);
                    beatRV.setItemAnimator(new DefaultItemAnimator());

                    adapter.notifyDataSetChanged();
                    beatRV.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }else {

                    noNotificationLAYOUT.setVisibility(View.VISIBLE);
                    beatRV.setVisibility(View.GONE);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
