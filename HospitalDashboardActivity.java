package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.anuj.appointmentrequest.Constants.EMAIL;
import static com.example.anuj.appointmentrequest.Constants.NAME;

public class HospitalDashboardActivity extends AppCompatActivity {
    private TextView tv_message;
    private SharedPreferences pref2;
    Button b1, b2, b3,b4,b5,b6;


    android.support.v7.widget.Toolbar toolbar;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(HospitalDashboardActivity.this,HomeActivity.class));
        finish();}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_dashboard);
        pref2 = getApplicationContext().getSharedPreferences("MyPref2",MODE_PRIVATE);


        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4 = (Button) findViewById(R.id.b4);
        b5=(Button)findViewById(R.id.b5);
        b6=(Button)findViewById(R.id.b6);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalDashboardActivity.this,DocUploadActivity.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalDashboardActivity.this,TipsUploadActivity.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalDashboardActivity.this,SlotUploadActivity.class));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref2.edit();
                editor.putBoolean(Constants_hospital.IS_LOGGED_IN,false);
                editor.putString(EMAIL,"");
                editor.putString(NAME,"");
                editor.putString(Constants_hospital.UNIQUE_ID,"");
                editor.apply();
               goToLogin();
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(HospitalDashboardActivity.this,DocAppointment.class));
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HospitalDashboardActivity.this,patientActivity.class));
            }
        });

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ActionBar actionBar = getSupportActionBar();

        View view = LayoutInflater.from(this).inflate(R.layout.app_bar, null);


        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            final View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);

            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);
            final TextView customTitle1 = (TextView) customView.findViewById(R.id.actionbartitle2);
            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            customTitle1.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title


            // Apply the custom view
            actionBar.setCustomView(customView);
        }





    }


    private void goToLogin() {

        startActivity(new Intent(HospitalDashboardActivity.this,HomeActivity.class));
        finish();
    }
}