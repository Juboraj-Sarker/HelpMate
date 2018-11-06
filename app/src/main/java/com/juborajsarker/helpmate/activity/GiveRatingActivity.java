package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.model.ReviewModel;

public class GiveRatingActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText ratingTextET;
    Button submitBTN;
    String expertUid, expertName, myUid;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_rating);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        expertUid = getIntent().getStringExtra("expertUid");
        expertName = getIntent().getStringExtra("expertName");
        myUid = FirebaseAuth.getInstance().getUid();
        init();
    }

    private void init() {

        ratingBar = (RatingBar) findViewById(R.id.rating_BAR);
        ratingTextET = (EditText) findViewById(R.id.rating_ET);
        submitBTN = (Button) findViewById(R.id.submit_BTN);

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                float rate = ratingBar.getRating();
                String ratingText = ratingTextET.getText().toString();

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Review/" + expertUid);
                String reviewId = reference.push().getKey();
                ReviewModel reviewModel = new ReviewModel();
                reviewModel.setReviewId(reviewId);
                reviewModel.setReviewGivenById(myUid);
                reviewModel.setReviewGivenByName("Test User");
                reviewModel.setReviewText(ratingText);
                reviewModel.setReviewRete(String.valueOf(rate));
                reviewModel.setReviewGivenToId(expertUid);
                reviewModel.setReviewGivenToName(expertName);

                reference.child(reviewId).setValue(reviewModel);
                Intent intent = new Intent(GiveRatingActivity.this, ViewReviewActivity.class);
                intent.putExtra("expertUid", expertUid);
                startActivity(intent);



                

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
