package com.juborajsarker.helpmate.activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.juborajsarker.helpmate.R;
import com.juborajsarker.helpmate.fragment.HomeFragment;
import com.juborajsarker.helpmate.fragment.InboxFragment;
import com.juborajsarker.helpmate.fragment.NotificationFragment;
import com.juborajsarker.helpmate.fragment.ProfileFragment;
import com.juborajsarker.helpmate.fragment.SearchExpertFragment;
import com.juborajsarker.helpmate.java_class.BottomNavigationViewHelper;

public class BottomNavigationActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
        setTitle("Help Mate");
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home: {

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            new HomeFragment()).commit();
                    return true;

                }case R.id.navigation_inbox: {

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            new InboxFragment()).commit();
                    return true;
                }
                case R.id.navigation_search: {

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            new SearchExpertFragment()).commit();
                    return true;
                }
                case R.id.navigation_notifications: {

                    fragmentManager.beginTransaction().replace(R.id.fragment_container, new
                            NotificationFragment()).commit();
                    return true;

                }
                case R.id.navigation_profile: {

                    fragmentManager.beginTransaction().replace(R.id.fragment_container,
                            new ProfileFragment()).commit();
                    return true;

                }
            }
            return false;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_in_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.menu_change_language) {


        }


        if (id == R.id.menu_logout) {

            logOut();
        }

        if (id == R.id.menu_exit) {

            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("Thanks for using my app")
                    .setMessage("\nAre you sure you want to really exit?")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            AppExit();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    })
                    .show();

        }


        return super.onOptionsItemSelected(item);
    }


    private void logOut() {

        sharedPreferences = getSharedPreferences("loginStatus", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
        startActivity(new Intent(BottomNavigationActivity.this, LoginActivity.class));
    }


    public void AppExit() {

        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);


    }


    public void rateApps() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }


    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

}
