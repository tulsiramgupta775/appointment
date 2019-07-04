package com.example.anuj.appointmentrequest;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class TipsActivity extends AppCompatActivity {
    TextView textv1,t4;
    ImageView iv2;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        String tipscategory = getIntent().getStringExtra("tipscategory");
        String tips=getIntent().getStringExtra("tips");

        String thumbnail = getIntent().getStringExtra("image");

        textv1 = (TextView) findViewById(R.id.textv1);
        textv1.setText(tipscategory);
        t4 = (TextView) findViewById(R.id.t4);
        t4.setText(tips);

        iv2 = (ImageView) findViewById(R.id.iv2);



        Glide.with(this)
                .load(thumbnail)
                .into(iv2);

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        final ActionBar actionBar = getSupportActionBar();

        View view = LayoutInflater.from(this).inflate(R.layout.app_bar, null);



        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            final View customView = getLayoutInflater().inflate(R.layout.actionbar_title1, null);
            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);

            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title
            actionBar.setDisplayHomeAsUpEnabled(true);


            // Apply the custom view
            actionBar.setCustomView(customView);
        }


    }
}
