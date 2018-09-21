package com.juborajsarker.helpmate.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.juborajsarker.helpmate.R;

public class UserDetailsActivity extends AppCompatActivity {

    ImageView userIV;
    TextView fullNameTV, userNameTV, phoneTV, emailTV, cityTV, countryTV;
    LinearLayout emailLAYOUT, callLAYOUT, messageLAYOUT;


    String fullName, userName, phone, email, city, country, toUserId;
    public static final int MY_PERMISSIONS_REQUEST_CALL = 52;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        checkCallPermission();
        init();
        receiveData();
        setTitle(fullName);

    }

    private void init() {

        userIV = (ImageView) findViewById(R.id.user_IV);
        fullNameTV = (TextView) findViewById(R.id.full_name_TV);
        userNameTV = (TextView) findViewById(R.id.user_name_TV);
        phoneTV = (TextView) findViewById(R.id.phone_TV);
        emailTV = (TextView) findViewById(R.id.email_TV);
        cityTV = (TextView) findViewById(R.id.city_name_TV);
        countryTV = (TextView) findViewById(R.id.country_name_TV);
        emailLAYOUT = (LinearLayout) findViewById(R.id.send_email_LAYOUT);
        callLAYOUT = (LinearLayout) findViewById(R.id.send_call_LAYOUT);
        messageLAYOUT = (LinearLayout) findViewById(R.id.send_message_LAYOUT);



        emailLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                sendEmail(emailTV.getText().toString());

            }
        });

        callLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                makeCall(phoneTV.getText().toString());

            }
        });


        messageLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(UserDetailsActivity.this, ChattingActivity.class);
                intent.putExtra("toUserId", toUserId);
                intent.putExtra("fullName", fullName);
                startActivity(intent);

            }
        });

    }


    public void receiveData(){

        Intent intent = getIntent();
        toUserId = intent.getStringExtra("toUserId");
        fullName = intent.getStringExtra("fullName");
        userName = intent.getStringExtra("userName");
        phone = intent.getStringExtra("phone");
        email = intent.getStringExtra("email");
        city = intent.getStringExtra("city");
        country = intent.getStringExtra("country");



        fullNameTV.setText(fullName);
        userNameTV.setText(userName);
        phoneTV.setText(phone);
        emailTV.setText(email);
        cityTV.setText(city);
        countryTV.setText(country);


    }


    public void makeCall(String number){

        if (phoneTV.getText().toString().equals("")){

            Toast.makeText(UserDetailsActivity.this, "No number found for phone call !!!", Toast.LENGTH_SHORT).show();


        }else {


            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));

            if (!checkCallPermission()) {

                checkCallPermission();

            }else {

                startActivity(callIntent);
            }


        }
    }


    public void sendEmail(String email) {

        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(Intent.EXTRA_TEXT   , "Hello ,");

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(UserDetailsActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }


    public boolean checkCallPermission() {


        if (ContextCompat.checkSelfPermission(UserDetailsActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {


            ActivityCompat.requestPermissions(UserDetailsActivity.this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL);


            return false;

        } else {

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
