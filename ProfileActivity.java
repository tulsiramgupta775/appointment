package com.example.anuj.appointmentrequest;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    SharedPreferences pref;
    TextView t3,t4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final ActionBar actionBar = getSupportActionBar();

        View view = LayoutInflater.from(this).inflate(R.layout.app_bar, null);
        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        t3 = (TextView) findViewById(R.id.t3);
        t3.setText("Welcome : " + pref.getString(Constants.NAME, ""));
       t4 = (TextView) findViewById(R.id.t4);
        t4.setText(pref.getString(Constants.EMAIL, ""));

        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            final View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);

            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);
            TextView customTitle1 = (TextView) customView.findViewById(R.id.actionbartitle2);
            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            customTitle1.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title
            actionBar.setDisplayHomeAsUpEnabled(true);

            // Apply the custom view
            actionBar.setCustomView(customView);
        }
    }

}
