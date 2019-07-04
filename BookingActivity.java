package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.ConditionVariable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import org.json.JSONArray;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anuj.appointmentrequest.models.doctors;
import com.example.anuj.appointmentrequest.models.doctorsAdapter;
import com.example.anuj.appointmentrequest.models.slots;
import com.example.anuj.appointmentrequest.models.slotsAdapter;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookingActivity extends AppCompatActivity {

    Calendar date;
    TextView et_date;
    TextView Morning, afetrnoon, evening;
    String currentDateString;
String dayOfTheWeek;
String docname,fees,User;
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);

        User=pref.getString(Constants.NAME, "");

        docname = getIntent().getStringExtra("title");
        fees= getIntent().getStringExtra("price");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                et_date.performClick();

            }
        },10);
         Morning=(TextView) findViewById(R.id.Morning);
        afetrnoon=(TextView) findViewById(R.id.Afetrnoon);
        evening=(TextView) findViewById(R.id.Evening);
        et_date = (TextView) findViewById(R.id.et_date);
        Morning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Morning.setEnabled(false);
                evening.setEnabled(true);
               afetrnoon.setEnabled(true);
                Morning.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
                afetrnoon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                evening.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                MorningSlotFragment fragment= new MorningSlotFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dayname", dayOfTheWeek );
               fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction ft =fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }

        });
        afetrnoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                afetrnoon.setEnabled(false);
                evening.setEnabled(true);
                Morning.setEnabled(true);
                afetrnoon.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
                Morning.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                evening.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
               noonslotFragment fragment= new noonslotFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dayname", dayOfTheWeek );
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction ft =fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }

        });
       evening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evening.setEnabled(false);
                Morning.setEnabled(true);
                afetrnoon.setEnabled(true);
                evening.setBackgroundColor(getResources().getColor(R.color.cardview_shadow_start_color));
                afetrnoon.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                Morning.setBackgroundColor(getResources().getColor(R.color.colorPrimary)); android.support.v4.app.FragmentManager fragmentManager=getSupportFragmentManager();
                EveSlotFragment fragment= new EveSlotFragment();
                Bundle bundle = new Bundle();
                bundle.putString("dayname", dayOfTheWeek );
                fragment.setArguments(bundle);
                android.support.v4.app.FragmentTransaction ft =fragmentManager.beginTransaction();
                ft.replace(R.id.fragment_frame,fragment);
                ft.commit();
            }

        });
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentDate = Calendar.getInstance();
                date = Calendar.getInstance();


                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                       date.set(Calendar.YEAR,year);
                        date.set(Calendar.MONTH,monthOfYear);
                        date.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                         currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(date.getTime());
                        et_date.setText(currentDateString);
                       dayOfTheWeek = String.valueOf(android.text.format.DateFormat.format("EEEE", date));

                        Morning.performClick();




                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(BookingActivity.this, dateSetListener, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();





            }

        });


    }




    public String getDocname(){

        return docname;
    }
    public String getday(){
        return dayOfTheWeek;
    }
    public String getFees(){
        return fees;
    }
    public String getDate(){
        return currentDateString;
    }
    public String getUser(){
        return User;
    }


}



