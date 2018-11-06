package com.juborajsarker.helpmate.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.HireAdapter;
import com.juborajsarker.helpmate.model.HireModel;

import java.util.ArrayList;
import java.util.List;

public class MyHelperActivity extends AppCompatActivity {

    RecyclerView helperRV;
    DatabaseReference reference;
    String uid;
    List<HireModel> hireModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_helper);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setTitle("My Helper");

        uid = FirebaseAuth.getInstance().getUid();
        reference = FirebaseDatabase.getInstance().getReference("Hire/" + uid);

        init();
    }

    private void init() {

        helperRV = (RecyclerView) findViewById(R.id.helper_RV);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HireModel hireModel = snapshot.getValue(HireModel.class);
                    hireModelList.add(hireModel);
                }


                HireAdapter hireAdapter = new HireAdapter(MyHelperActivity.this, MyHelperActivity.this, hireModelList);
                RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(MyHelperActivity.this, 1);
                helperRV.setLayoutManager(layoutManagerBeforeMeal);
                helperRV.setItemAnimator(new DefaultItemAnimator());

                hireAdapter.notifyDataSetChanged();
                helperRV.setAdapter(hireAdapter);
                hireAdapter.notifyDataSetChanged();
            }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home: {

                super.onBackPressed();

            }
            default: {

                return super.onOptionsItemSelected(item);
            }


        }

    }
}
