package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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


    String fullName, userName, phone, email, city, country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        init();

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

        receiveData();

        emailLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        callLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });


        messageLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }


    public void receiveData(){

        Intent intent = getIntent();
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

}
