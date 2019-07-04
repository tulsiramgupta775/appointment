package com.example.anuj.appointmentrequest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.example.anuj.appointmentrequest.models.slots;
import com.example.anuj.appointmentrequest.models.slotsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link noonslotFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link noonslotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class noonslotFragment extends Fragment implements View.OnClickListener{

    List<slots> slots_List;
    String docname;
    //the recyclerview
    RecyclerView recyclerView;
    String day;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        day = this.getArguments().getString("dayname");
        View view = inflater.inflate(R.layout.fragment_morning_slot,container,false);
        initViews(view);
        return view;
    }

    private void initViews(View view){



        docname = getActivity().getIntent().getStringExtra("title");
        slots_List = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recylcerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        loadslots();
    }

    @Override
    public void onClick(View v) {



    }

    private void loadslots(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://anujagrawalpm.000webhostapp.com/appointment/timeslots.php?"+"docname="+docname.replace(" ", "%20")+"&day="+day+"&daytime=Afternoon",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String result) {
                        try {
                            JSONArray jsonarray = new JSONArray(result);
                            //traversing through all the object
                            for (int i = 0; i < jsonarray.length(); i++) {
                                //getting product object from json array
                                JSONObject slots = jsonarray.getJSONObject(i);

                                //adding the product to product list
                                slots_List.add(new slots(
                                        slots.getInt("id"),
                                        slots.getString("from_time"),
                                        slots.getString("to_time")
                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            slotsAdapter adapter = new slotsAdapter(getActivity(), slots_List);
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
        Volley.newRequestQueue(getActivity()).add(stringRequest);


    }






}
