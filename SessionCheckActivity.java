package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SessionCheckActivity extends AppCompatActivity {
    SharedPreferences pref2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_check);
        pref2 = getApplicationContext().getSharedPreferences("MyPref2",MODE_PRIVATE);
        if(pref2.getBoolean(Constants_hospital.IS_LOGGED_IN,false)){
            startActivity(new Intent(SessionCheckActivity.this,HospitalDashboardActivity.class));

        }else {
            startActivity(new Intent(SessionCheckActivity.this,DoctorsLoginActivity.class));
        }
    }
}
