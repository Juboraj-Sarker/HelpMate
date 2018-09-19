package com.juborajsarker.helpmate.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.juborajsarker.helpmate.R;

/**
 * Created by jubor on 11/15/2017.
 */

public class ViewDialog {

    Activity activity;

    public void showDialog(final Activity activity, String userName, String fullName, String address, String subCity, String city, String division,
                           String posterCode, String country, final String phone, double lat, double lng) {

        final Dialog dialog = new Dialog(activity, android.R.style.Theme_Material_Light_NoActionBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_layout);


        TextView userTV = (TextView) dialog.findViewById(R.id.dialog_userName_TV);
        userTV.setText(userName);

        TextView fullNameTV = (TextView) dialog.findViewById(R.id.dialog_fullName_TV);
        fullNameTV.setText(fullName);

        TextView addressTV = (TextView) dialog.findViewById(R.id.dialog_address_TV);
        addressTV.setText(address);

        TextView subCityTV = (TextView) dialog.findViewById(R.id.dialog_subcity_TV);
        subCityTV.setText(subCity);

        TextView cityTV = (TextView) dialog.findViewById(R.id.dialog_city_TV);
        cityTV.setText(city);

        TextView divisionTV = (TextView) dialog.findViewById(R.id.dialog_division_TV);
        divisionTV.setText(division);

        TextView posterCodeTV = (TextView) dialog.findViewById(R.id.dialog_postercode_TV);
        posterCodeTV.setText(posterCode);

        TextView countryTV = (TextView) dialog.findViewById(R.id.dialog_country_TV);
        countryTV.setText(country);

        this.activity = activity;


        TextView callTV = (TextView) dialog.findViewById(R.id.dialog_call_TV);
        callTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeCall(phone);
            }
        });


        TextView messageTV = (TextView) dialog.findViewById(R.id.dialog_message_TV);
        messageTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        TextView mapTV = (TextView) dialog.findViewById(R.id.dialog_map_TV);
        mapTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        Button okButton = (Button) dialog.findViewById(R.id.dialog_ok_BTN);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });


        dialog.show();


    }

    private void makeCall(String phone) {

        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        activity.startActivity(intent);
    }
}
