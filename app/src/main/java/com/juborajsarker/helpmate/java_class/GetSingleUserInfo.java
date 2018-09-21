package com.juborajsarker.helpmate.java_class;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.juborajsarker.helpmate.activity.UserDetailsActivity;
import com.juborajsarker.helpmate.model.UserModel;

public class GetSingleUserInfo {

    DatabaseReference reference;
    Context context;
    String uid;

    String userName;
    String fullName;
    String userPhone;
    String userEmail;
    String userCity;
    String userCountry;

    public GetSingleUserInfo(DatabaseReference reference, final Context context, final String uid) {

        this.reference = reference;
        this.context = context;
        this.uid = uid;
    }


    public void initializeData(DatabaseReference reference){

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    UserModel userModel = snapshot.getValue(UserModel.class);

                    if (userModel.getUserID().equals(uid)) {

                        fullName = userModel.getFullName();
                        userName = userModel.getUserName();
                        userPhone = userModel.getPhone();
                        userEmail = userModel.getEmail();
                        userCity = userModel.getCity();
                        userCountry = userModel.getCountry();

                        setFullName(fullName);
                        setUserName(userName);
                        setUserPhone(userPhone);
                        setUserEmail(userEmail);
                        setUserCity(userCity);
                        setUserCountry(userCountry);


                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    private void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    private void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    private void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    private void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    private void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }
}
