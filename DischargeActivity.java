package com.example.anuj.appointmentrequest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class DischargeActivity extends AppCompatActivity {
    Toolbar toolbar;
    int id;
    String bed,patient,mobile,user;
    TextView Patient,Mobile,User;
   EditText Amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discharge);
        id = getIntent().getIntExtra("id",0);
        bed = getIntent().getStringExtra("Bed");
         patient= getIntent().getStringExtra("Patient");
       mobile = getIntent().getStringExtra("Mobile");
        user = getIntent().getStringExtra("User");

        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Patient=(TextView)findViewById(R.id.z5);
        Mobile=(TextView)findViewById(R.id.z6);
        User=(TextView)findViewById(R.id.z7);
        Amount=(EditText) findViewById(R.id.z8);
        Patient.setText(patient);
        Mobile.setText(mobile);
        User.setText(user);
        Amount.getText().toString();


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



















        loadBookings();

    }


    private void loadBookings() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://anujagrawalpm.000webhostapp.com/appointment/Updatepatients.php?id="+id+"&Bed="+bed+"&patient="+patient,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(DischargeActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });


        Volley.newRequestQueue(this).add(stringRequest);
    }


}

