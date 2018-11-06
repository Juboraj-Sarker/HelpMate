package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.model.HireModel;

public class BitDetailsActivity extends AppCompatActivity {

    TextView postTV, bitByTV, bitDescriptionTV, bitBudgetTV, bitEstimatedTimeTV, bitExpectedStartDateTV;
    Button hireBTN;
    String postText, bitBy, bitDescription, bitBudget, bitEstimatedTime, bitExpectedStartDate, expertUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bit_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        setTitle("Bit Details");

        init();
    }

    private void init() {

        Intent intent = getIntent();
        postText = intent.getStringExtra("postText");
        bitBy = intent.getStringExtra("bitBy");
        bitDescription = intent.getStringExtra("bitDescription");
        bitBudget = intent.getStringExtra("bitBudget");
        bitEstimatedTime = intent.getStringExtra("bitEstimatedTime");
        bitExpectedStartDate = intent.getStringExtra("bitExpectedStartDate");
        expertUid = intent.getStringExtra("expertUid");

        postTV = (TextView) findViewById(R.id.post_TV);
        bitByTV = (TextView) findViewById(R.id.bit_by_TV);
        bitDescriptionTV = (TextView) findViewById(R.id.bit_description_TV);
        bitBudgetTV = (TextView) findViewById(R.id.bit_price_TV);
        bitEstimatedTimeTV = (TextView) findViewById(R.id.bit_estimated_time_TV);
        bitExpectedStartDateTV = (TextView) findViewById(R.id.bit_start_date_TV);
        hireBTN = (Button) findViewById(R.id.hire_BTN);

        postTV.setText(postText);
        bitByTV.setText(bitBy);
        bitDescriptionTV.setText(bitDescription);
        bitBudgetTV.setText(bitBudget);
        bitEstimatedTimeTV.setText(bitEstimatedTime);
        bitExpectedStartDateTV.setText(bitExpectedStartDate);

        hireBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uid = FirebaseAuth.getInstance().getUid();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Hire/" + uid);

                String hireId = reference.push().getKey();
                HireModel hireModel = new HireModel();
                hireModel.setHireDate(new DateTimeConverter().getCurrentDate());
                hireModel.setHireTime(new DateTimeConverter().getCurrentTime());
                hireModel.setHireExpertName(bitBy);
                hireModel.setHireExpertUserId(expertUid);
                hireModel.setHireId(hireId);

                reference.child(hireId).setValue(hireModel);

                startActivity(new Intent(BitDetailsActivity.this, MyHelperActivity.class));




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
