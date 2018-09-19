package com.juborajsarker.helpmate.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.ForgetPasswordActivity;
import com.juborajsarker.helpmate.activity.LoginActivity;
import com.juborajsarker.helpmate.model.UserModel;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class ProfileFragment extends Fragment {

    View view;
    SharedPreferences sharedPreferences;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    List<UserModel> modelList = new ArrayList<>();

    TextView fullNameTV, userNameTV, emailTV, accountStatusTV;
    ImageView accountStatusIV;
    LinearLayout changePassLAYOUT, logoutLAYOUT;
    ImageView userIV;

    String uid;


    public ProfileFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_profile, container, false);


        init();
        loadData();
        setData();

        return view;
    }


    private void loadData() {

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            uid = user.getUid();
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("User/All/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserModel model = snapshot.getValue(UserModel.class);
                    modelList.add(model);

                    if (model.getUserID().equals(uid)){

                        userNameTV.setText(model.getUserName());
                        emailTV.setText(model.getEmail());
                        fullNameTV.setText(model.getFullName());

                        if (model.isAccountIsActivate()){

                            accountStatusTV.setText("Active");
                            accountStatusIV.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_active));

                        }else {

                            accountStatusTV.setText("In Active");
                            accountStatusIV.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.ic_inactive));
                        }

                    }

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


    private void setData() {


    }

    private void init() {

        fullNameTV = (TextView) view.findViewById(R.id.full_name_TV);
        userNameTV = (TextView) view.findViewById(R.id.user_name_TV);
        emailTV = (TextView) view.findViewById(R.id.email_TV);
        accountStatusTV = (TextView) view.findViewById(R.id.account_status_TV);

        accountStatusIV = (ImageView) view.findViewById(R.id.account_status_IV);

        changePassLAYOUT = (LinearLayout) view.findViewById(R.id.change_password_LAYOUT);
        logoutLAYOUT = (LinearLayout) view.findViewById(R.id.logout_LAYOUT);

        userIV = (ImageView) view.findViewById(R.id.user_IV);


        changePassLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getContext().startActivity(new Intent(getContext(), ForgetPasswordActivity.class));

            }
        });


        logoutLAYOUT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                logOut();
            }
        });

    }


    private void logOut() {

        sharedPreferences = getActivity().getSharedPreferences("loginStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(getContext(), LoginActivity.class));
    }

}
