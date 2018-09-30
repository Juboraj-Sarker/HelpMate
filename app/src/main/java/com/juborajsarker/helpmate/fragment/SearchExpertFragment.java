package com.juborajsarker.helpmate.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.adapter.ExpertListAdapter;
import com.juborajsarker.helpmate.java_class.GridSpacingItemDecoration;
import com.juborajsarker.helpmate.model.UserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class SearchExpertFragment extends Fragment {

    View view;
    Spinner categorySP;
    EditText cityET;
    ImageView searchIV;
    RecyclerView expertRV;

    String city, category, country;
    List<UserModel> expertList = new ArrayList<>();

    ProgressDialog progressDialog;




    public SearchExpertFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_expert, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        actionBar.setTitle("Search Expert");

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Searching ... Please wait");
        progressDialog.setCancelable(true);
        init();


        return view;
    }

    private void init() {

        categorySP = (Spinner)view.findViewById(R.id.SP_category);
        cityET = (EditText)view.findViewById(R.id.city_ET);
        searchIV = (ImageView) view.findViewById(R.id.search_IV);
        expertRV = (RecyclerView) view.findViewById(R.id.expert_RV);

        searchIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                 if (checkValidity()){


                     progressDialog.show();

                     city = cityET.getText().toString();
                     category = (String) categorySP.getSelectedItem();
                     country = getUserCountry(getContext());

                     DatabaseReference reference = FirebaseDatabase.getInstance().getReference
                             ("User/Expert/"+ country+"/" + category+"/" + city);

                     Log.d("database", "User/Expert/"+ country+"/" + category+"/" + city);

                     reference.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             expertList.clear();

                             for (DataSnapshot snapshot: dataSnapshot.getChildren()){

                                 UserModel user = snapshot.getValue(UserModel.class);
                                 expertList.add(user);

                             }

                             ExpertListAdapter adapter = new ExpertListAdapter(getActivity(), getContext(), expertList);
                             RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
                             expertRV.setLayoutManager(layoutManager);
                             expertRV.addItemDecoration(new GridSpacingItemDecoration(1, 0, true));
                             expertRV.setItemAnimator(new DefaultItemAnimator());

                             expertRV.setAdapter(adapter);
                             adapter.notifyDataSetChanged();

                             progressDialog.dismiss();

                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                             progressDialog.dismiss();

                         }
                     });


                 }










            }
        });


    }


    public boolean checkValidity(){

        if (cityET.getText().toString().equals("")){

            cityET.setError("Please enter a valid city");
            return false;

        }else if (cityET.getText().toString().length()>0 && Character.isWhitespace(cityET.getText().toString().charAt(0))){

            cityET.setError("City name cannot start with a space");
            return false;

        }else if (categorySP.getSelectedItemPosition() == 0){

            Toast.makeText(getContext(), "Please select a valid category !!", Toast.LENGTH_SHORT).show();
            return false;

        }else {

            return true;
        }

    }


    public String getUserCountry(Context context) {
        try {
            final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            final String simCountry = tm.getSimCountryIso();
            if (simCountry != null && simCountry.length() == 2) { // SIM country code is available

                Locale loc = new Locale("", simCountry);
                return loc.getDisplayCountry();

            }
            else if (tm.getPhoneType() != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
                String networkCountry = tm.getNetworkCountryIso();
                if (networkCountry != null && networkCountry.length() == 2) { // network country code is available

                    Locale loc = new Locale("", networkCountry);
                    return loc.getDisplayCountry();
                }
            }
        }
        catch (Exception e) {

            Log.d("error", e.getMessage());
        }
        return null;
    }
}
