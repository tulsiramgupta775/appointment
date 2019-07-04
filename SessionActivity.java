package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SessionActivity extends AppCompatActivity {
    SharedPreferences pref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            startActivity(new Intent(SessionActivity.this,DashboardActivity.class));

        }else {
            startActivity(new Intent(SessionActivity.this,LoginActivity.class));
        }

    }
}
