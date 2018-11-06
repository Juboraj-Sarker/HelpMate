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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.ReviewAdapter;
import com.juborajsarker.helpmate.model.ReviewModel;

import java.util.ArrayList;
import java.util.List;

public class ViewReviewActivity extends AppCompatActivity {

    RecyclerView reviewRV;
    DatabaseReference reference;
    String expertUid;
    List<ReviewModel> reviewModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setTitle("View Review");


        expertUid = getIntent().getStringExtra("expertUid");
        reference = FirebaseDatabase.getInstance().getReference("Review/" + expertUid);

        init();
    }

    private void init() {

        reviewRV = (RecyclerView) findViewById(R.id.review_RV);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                    ReviewModel reviewModel = snapshot.getValue(ReviewModel.class);
                    reviewModelList.add(reviewModel);
                }

                ReviewAdapter reviewAdapter = new ReviewAdapter(ViewReviewActivity.this, ViewReviewActivity.this, reviewModelList);
                RecyclerView.LayoutManager layoutManagerBeforeMeal = new GridLayoutManager(ViewReviewActivity.this, 1);
                reviewRV.setLayoutManager(layoutManagerBeforeMeal);
                reviewRV.setItemAnimator(new DefaultItemAnimator());

                reviewAdapter.notifyDataSetChanged();
                reviewRV.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();


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
