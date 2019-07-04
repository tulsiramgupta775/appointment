package com.example.anuj.appointmentrequest;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anuj.appointmentrequest.models.Booking;
import com.example.anuj.appointmentrequest.models.BookingAdapter;
import com.example.anuj.appointmentrequest.models.prevbookAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;



public class PreviousAppointmentFragment extends Fragment implements View.OnClickListener{

    List<Booking> Booking_List;
    String docname;
    //the recyclerview
    RecyclerView recyclerViewbooking;
    SharedPreferences pref;
    String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_previous_appointment,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        pref = getActivity().getSharedPreferences("MyPref",MODE_PRIVATE);
        user= pref.getString(Constants.NAME, "");
        recyclerViewbooking = view.findViewById(R.id.recylcerViewBooking);
        recyclerViewbooking.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewbooking.setLayoutManager(mLayoutManager);
        Booking_List = new ArrayList<>();
        loadBookings();
    }

    @Override
    public void onClick(View v) {



    }

    private void loadBookings() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://anujagrawalpm.000webhostapp.com/appointment/getbooks.php?user=" + user+"&status=Discharged",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array = object.getJSONArray("bookings");


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
                            prevbookAdapter adapter = new prevbookAdapter(getActivity(), Booking_List);
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);
    }}





