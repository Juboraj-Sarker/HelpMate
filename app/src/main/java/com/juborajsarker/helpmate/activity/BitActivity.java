package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.java_class.DateTimeConverter;
import com.juborajsarker.helpmate.model.BeatModel;
import com.juborajsarker.helpmate.model.UserModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BitActivity extends AppCompatActivity {

    TextView userNameTV, postTV;
    EditText bitPriceET, bitDescriptionET, estimatedTimeET, expectedStartDateET;
    Button bitBTN;

    String uid, postId, userName, post, description, estimatedTime, expectedStartDate, expertUid, expertUserName;
    int price;
    Date currentDate, givenDate;

    List<UserModel> modelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        expertUid = FirebaseAuth.getInstance().getUid();
        getExpertUserName(expertUid);
        init();
        receiveData();

    }

    private void getExpertUserName(String expertUid) {

       DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User/All/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserModel model = snapshot.getValue(UserModel.class);
                    modelList.add(model);

                    if (model.getUserID().equals(uid)){


                        expertUserName = model.getUserName();


                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void init() {

        userNameTV = (TextView) findViewById(R.id.user_name_TV);
        postTV = (TextView) findViewById(R.id.post_TV);
        bitPriceET = (EditText) findViewById(R.id.bit_price_ET);
        bitDescriptionET = (EditText) findViewById(R.id.bit_description_ET);
        estimatedTimeET = (EditText) findViewById(R.id.estimated_time_ET);
        expectedStartDateET = (EditText) findViewById(R.id.expected_start_date_ET);
        bitBTN = (Button) findViewById(R.id.bit_BTN);

        bitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    if (checkValidity() && checkDateValidity(expectedStartDateET.getText().toString())
                            && checkFutureDate(expectedStartDateET.getText().toString())) {


                        description = bitDescriptionET.getText().toString();
                        estimatedTime = estimatedTimeET.getText().toString();
                        expectedStartDate = expectedStartDateET.getText().toString();
                        price = Integer.parseInt(bitPriceET.getText().toString());

                        pushData(description, estimatedTime, expectedStartDate, price);


                    } else {

                        if (!checkDateValidity(expectedStartDateET.getText().toString())) {

                            expectedStartDateET.setError("Please enter a valid date");

                        } else if (!checkFutureDate(expectedStartDateET.getText().toString())) {

                            expectedStartDateET.setError("Please select a future date. You cannot start in past");
                        }
                    }

                } catch (Exception e) {


                    if (e.getMessage().contains("For input string")){

                        bitPriceET.setError("Floating number is not acceptable");
                        Toast.makeText(BitActivity.this, "Floating number is not acceptable", Toast.LENGTH_SHORT).show();
                    }
                    // e.printStackTrace();
                }
            }
        });

    }



    private void receiveData() {

        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        post = intent.getStringExtra("post");
        uid = intent.getStringExtra("uid");
        postId = intent.getStringExtra("postId");

        userNameTV.setText(userName);
        postTV.setText(post);
    }

    private void pushData(String description, String estimatedTime, String expectedStartDate, int price) {

        DateTimeConverter converter = new DateTimeConverter();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference
                ("Beat/All/" + uid);
        String bitId = reference.push().getKey();

        BeatModel bitModel = new BeatModel();
        bitModel.setBitId(bitId);
        bitModel.setPostId(postId);
        bitModel.setUid(uid);
        bitModel.setBitPrice(price);
        bitModel.setDescription(description);
        bitModel.setEstimatedTime(estimatedTime);
        bitModel.setExpectedStartDate(expectedStartDate);
        bitModel.setBitDate(converter.getCurrentDate());
        bitModel.setBitTime(converter.getCurrentTime());
        bitModel.setExpertUid(expertUid);
        bitModel.setExpertName(userName);
        bitModel.setPostText(post);

        reference.child(bitId).setValue(bitModel);

        reference = FirebaseDatabase.getInstance().getReference
                ("Beat/Expert/" + expertUid);
        reference.child(bitId).setValue(bitModel);

        Toast.makeText(this, "Successfully placed bit", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
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

    private boolean checkValidity() {

        if (bitPriceET.getText().toString().equals("")) {

            bitPriceET.setError("Please enter your bit price");
            return false;

        } else if (bitDescriptionET.getText().toString().equals("")) {

            bitDescriptionET.setError("Bit description is mandatory");
            return false;

        } else if (bitDescriptionET.getText().toString().length() > 0 &&
                Character.isWhitespace(bitDescriptionET.getText().toString().charAt(0))) {

            bitDescriptionET.setError("Description cannot start with a space");
            return false;

        } else if (estimatedTimeET.getText().toString().equals("")) {

            estimatedTimeET.setError("Please enter estimated time");
            return false;

        } else if (expectedStartDateET.getText().toString().equals("")) {

            expectedStartDateET.setError("Please enter expected date");
            return false;

        } else {

            return true;
        }
    }


    public static boolean checkDateValidity(String dateString) {

        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setLenient(false);

        String date = dateString;
        boolean valid = true;

        try {
            format.parse(date);
        } catch (ParseException e) {

            valid = false;


        }

        return valid;
    }


    public boolean checkFutureDate(String dateString) {

        String current = new SimpleDateFormat("dd/MM/yyyy").format(Calendar.getInstance().getTime());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String given = dateString;

        Log.d("Current: Given: " , ""+ current + " : " + given );

        try {
            format.parse(given);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
             currentDate = format.parse(current);
             givenDate = format.parse(given);


        } catch (ParseException e) {

            e.printStackTrace();
        }

        if (givenDate.after(currentDate)){

            return  true;

        }else if (givenDate.equals(currentDate)){

            return  true;

        }else {

            Log.d("Current: Given: " , "False" );
            return false;
        }



    }


}


