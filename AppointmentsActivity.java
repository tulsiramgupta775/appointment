package com.example.anuj.appointmentrequest;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anuj.appointmentrequest.models.Booking;
import com.example.anuj.appointmentrequest.models.BookingAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppointmentsActivity extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work


    //a list to store all the products

TextView current,previous;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);
        current=(TextView) findViewById(R.id.tvAppointmentC);
        previous=(TextView) findViewById(R.id.tvAppointmentPrev);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                current.performClick();
            }
        },10);
        current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                current.setEnabled(false);
                previous.setEnabled(true);
                previous.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                CurrentAppointmentFragment fragment= new CurrentAppointmentFragment();
                android.support.v4.app.FragmentTransaction ft =fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previous.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                previous.setEnabled(false);
                current.setEnabled(true);
                current.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                PreviousAppointmentFragment fragment= new PreviousAppointmentFragment();
                android.support.v4.app.FragmentTransaction ft =fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }
        });
        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ActionBar actionBar = getSupportActionBar();
        View view = LayoutInflater.from(this).inflate(R.layout.app_bar, null);
        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            final View customView = getLayoutInflater().inflate(R.layout.actionbar_title1, null);
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);
            customTitle.setTypeface(Typeface.MONOSPACE);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setCustomView(customView);
        }
        }
}

