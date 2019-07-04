package com.example.anuj.appointmentrequest;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.anuj.appointmentrequest.models.doctors;
import com.example.anuj.appointmentrequest.models.doctorsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailActivity extends AppCompatActivity {
TextView y_name,longdesc,shortdesc1,textViewRating1;
ImageView imageView1;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String title = getIntent().getStringExtra("title");
        String longdes=getIntent().getStringExtra("longdesc");
        String shortdesc = getIntent().getStringExtra("shortdesc");
        String rating = getIntent().getStringExtra("rating");

        y_name = (TextView) findViewById(R.id.y_name);
        y_name.setText(title);
        longdesc = (TextView) findViewById(R.id.longdesc1);
        longdesc.setText(longdes);
        shortdesc1 = (TextView) findViewById(R.id.textViewShortDesc1);
        shortdesc1.setText(shortdesc);
        textViewRating1 = (TextView) findViewById(R.id.textViewRating1);
        textViewRating1.setText(rating);
        imageView1 = (ImageView) findViewById(R.id.imageView1);




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


    }}
