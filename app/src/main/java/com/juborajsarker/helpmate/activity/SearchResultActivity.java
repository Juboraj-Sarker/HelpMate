package com.juborajsarker.helpmate.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;

import java.io.IOException;
import java.util.List;

public class SearchResultActivity extends AppCompatActivity {

    ListView expertLV;
    DatabaseReference databaseReference;
   // List<ComputerExpert> computerExperts;

    double lat, lng;
    String category;
    Address returnAddress;

    String country, city, subCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        lat = intent.getDoubleExtra("lat", 0);
        lng = intent.getDoubleExtra("lng", 0);
        category = intent.getStringExtra("category");

        expertLV = (ListView)findViewById(R.id.expert_LV);

       // computerExperts = new ArrayList<>();


        execute();

        country = returnAddress.getCountryName();
        city = returnAddress.getLocality();
        subCity = returnAddress.getSubLocality();

        if (subCity == null){

            Toast.makeText(this, country + "\n" + city, Toast.LENGTH_SHORT).show();
            databaseReference = FirebaseDatabase.getInstance().getReference
                    ("Expert/" + category + "/" + country + "/" + city);

        }else {

            Toast.makeText(this, country + "\n" + city + "\n" + subCity, Toast.LENGTH_LONG).show();
            databaseReference = FirebaseDatabase.getInstance().getReference
                    ("Expert/" + category + "/" + country + "/" + city + "/" + subCity);


        }




    }

    private void execute() {



        Geocoder geocoder = new Geocoder(SearchResultActivity.this);

        try {
            List<Address> addresses = geocoder.getFromLocation(lat,lng,1);
            if (geocoder.isPresent()) {
                StringBuilder stringBuilder = new StringBuilder();
                if (addresses.size()>0) {
                    returnAddress = addresses.get(0);
                }
            } else {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }


    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //computerExperts.clear();

                for (DataSnapshot computerSnapShot: dataSnapshot.getChildren()){

                 //   ComputerExpert computerExpert = computerSnapShot.getValue(ComputerExpert.class);
                  //  computerExperts.add(computerExpert);
                }

            //    ComputerExpertList adapter = new ComputerExpertList(SearchResultActivity.this, computerExperts);
             //   expertLV.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
