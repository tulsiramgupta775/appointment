package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BackToHomeActivity extends AppCompatActivity {
Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_back_to_home);

    b1=(Button)findViewById(R.id.w1);
b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(BackToHomeActivity.this,DashboardActivity.class));
    }
});

    }
}
