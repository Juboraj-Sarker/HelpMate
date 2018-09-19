package com.juborajsarker.helpmate.fragment;


import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.activity.SearchResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


public class SearchExpertFragment extends Fragment {

    View view;
    Spinner categorySP;
    EditText locationET;
    Button searchExpertBTN;

    double lat, lng;


    public SearchExpertFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search_expert, container, false);

        init();


        return view;
    }

    private void init() {

        categorySP = (Spinner)view.findViewById(R.id.SP_category);
        locationET = (EditText)view.findViewById(R.id.locationET);
        searchExpertBTN = (Button)view.findViewById(R.id.search_expert_BTN);

        searchExpertBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String address = locationET.getText().toString();
                String category = (String) categorySP.getSelectedItem();


                if (address.equals("")){

                    locationET.setError("This field is required !!!");

                }else {

                    getLatLongFromPlace(address);

                    if (lat !=0 && lng !=0){

                        Intent intent = new Intent(getContext(), SearchResultActivity.class);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lng", lng);
                        intent.putExtra("category", category);
                        startActivity(intent);


                    }
                }



                lat = 0;
                lng = 0;






            }
        });


    }


    public void getLatLongFromPlace(String place) {
        try {
            Geocoder selected_place_geocoder = new Geocoder(getContext());
            List<Address> address;

            address = selected_place_geocoder.getFromLocationName(place, 5);

            if (address == null) {
              //  d.dismiss();
                Toast.makeText(getContext(), "Invalid Address !!!", Toast.LENGTH_SHORT).show();
            } else {
                Address location = address.get(0);
                 lat= location.getLatitude();
                 lng = location.getLongitude();

            }

        } catch (Exception e) {
            e.printStackTrace();
            fetchLatLongFromService fetch_latlng_from_service_abc = new fetchLatLongFromService(
                    place.replaceAll("\\s+", ""));
            fetch_latlng_from_service_abc.execute();

        }

    }


//Sometimes happens that device gives location = null

    public class fetchLatLongFromService extends AsyncTask<Void, Void, StringBuilder> {
        String place;


        public fetchLatLongFromService(String place) {
            super();
            this.place = place;

        }

        @Override
        protected void onCancelled() {
            // TODO Auto-generated method stub
            super.onCancelled();
            this.cancel(true);
        }

        @Override
        protected StringBuilder doInBackground(Void... params) {
            // TODO Auto-generated method stub
            try {
                HttpURLConnection conn = null;
                StringBuilder jsonResults = new StringBuilder();
                String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
                        + this.place + "&sensor=false";

                URL url = new URL(googleMapUrl);
                conn = (HttpURLConnection) url.openConnection();
                InputStreamReader in = new InputStreamReader(
                        conn.getInputStream());
                int read;
                char[] buff = new char[1024];
                while ((read = in.read(buff)) != -1) {
                    jsonResults.append(buff, 0, read);
                }
                String a = "";
                return jsonResults;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(StringBuilder result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try {
                JSONObject jsonObj = new JSONObject(result.toString());
                JSONArray resultJsonArray = jsonObj.getJSONArray("results");

                // Extract the Place descriptions from the results
                // resultList = new ArrayList<String>(resultJsonArray.length());

                JSONObject before_geometry_jsonObj = resultJsonArray
                        .getJSONObject(0);

                JSONObject geometry_jsonObj = before_geometry_jsonObj
                        .getJSONObject("geometry");

                JSONObject location_jsonObj = geometry_jsonObj
                        .getJSONObject("location");

                String lat_helper = location_jsonObj.getString("lat");
                 lat = Double.valueOf(lat_helper);


                String lng_helper = location_jsonObj.getString("lng");
                lng = Double.valueOf(lng_helper);


            //    LatLng point = new LatLng(lat, lng);


            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();

            }
        }
    }

}
