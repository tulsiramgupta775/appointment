package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anuj.appointmentrequest.models.doctors;
import com.example.anuj.appointmentrequest.models.doctorsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DoctorsActivity extends AppCompatActivity {
    CardView cardView;
    android.support.v7.widget.Toolbar toolbar;

    //this is the JSON Data URL
    //make sure you are using the correct ip else it will not work
    private static final String URL_doctors = "http://192.168.2.10/appointment/Doctorslist.php";

    //a list to store all the products
    List<doctors> doctors_List;

    //the recyclerview
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors);


        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(this, 500);



        cardView=(CardView)findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DoctorsActivity.this, BookingActivity.class));


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
            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle1);

            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title


            actionBar.setDisplayHomeAsUpEnabled(true);
            // Apply the custom view
            actionBar.setCustomView(customView);
        }















        recyclerView = findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);


        //initializing the productlist
        doctors_List = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview




        recyclerView.setLayoutManager(layoutManager);
        loadproducts();

    }


    private void loadproducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_doctors,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject doctors = array.getJSONObject(i);

                                //adding the product to product list
                                doctors_List.add(new doctors(
                                        doctors.getInt("id"),
                                        doctors.getString("title"),
                                        doctors.getString("shortdesc"),
                                        doctors.getString("rating"),
                                        doctors.getString("price"),
                                        doctors.getString("image"),
                                        doctors.getString("longdesc")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            doctorsAdapter adapter = new doctorsAdapter(DoctorsActivity.this, doctors_List);
                            recyclerView.setAdapter(adapter);
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

