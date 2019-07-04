package com.example.anuj.appointmentrequest;

import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

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

public class DocAppointment extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work


    //a list to store all the products
    List<Booking> Booking_List;

    //the recyclerview
    RecyclerView recyclerViewbooking;
    SharedPreferences pref;
    String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_appointment);
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















        recyclerViewbooking = findViewById(R.id.recylcerViewBooking);
        recyclerViewbooking.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewbooking.setLayoutManager(mLayoutManager);
        //initializing the productlist
        Booking_List = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview




        loadBookings();

    }


    private void loadBookings() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://anujagrawalpm.000webhostapp.com/appointment/getbookings.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("bookings");


                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject Booking = array.getJSONObject(i);

                                //adding the product to product list
                                Booking_List.add(new Booking(
                                        Booking.getInt("id"),
                                        Booking.getString("Patient"),
                                        Booking.getString("Doctor"),
                                        Booking.getString("User"),
                                        Booking.getString("Date"),
                                        Booking.getString("Day"),
                                        Booking.getString("Time")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            BookingAdapter adapter = new BookingAdapter(DocAppointment.this, Booking_List);
                            recyclerViewbooking.setAdapter(adapter);
                        } catch (JSONException e) {
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

