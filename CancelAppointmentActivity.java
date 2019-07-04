package com.example.anuj.appointmentrequest;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
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

public class CancelAppointmentActivity extends AppCompatActivity {
Toolbar toolbar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cancel_appointment);



        id = getIntent().getIntExtra("id",0);


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


















        loadBookings();

    }


    private void loadBookings() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://anujagrawalpm.000webhostapp.com/appointment/Updatebooks.php?id="+id,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(CancelAppointmentActivity.this, ""+response, Toast.LENGTH_SHORT).show();
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

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }


}

