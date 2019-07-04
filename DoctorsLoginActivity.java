package com.example.anuj.appointmentrequest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.anuj.appointmentrequest.models.ServerRequest;
import com.example.anuj.appointmentrequest.models.ServerResponse;
import com.example.anuj.appointmentrequest.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DoctorsLoginActivity extends AppCompatActivity {

    private AppCompatButton btn_login;
    private EditText et_email,et_password;

    private ProgressBar progress;
    private SharedPreferences pref2;

    @Override
    public void onBackPressed() {
        startActivity(new Intent(DoctorsLoginActivity.this,HomeActivity.class));
        finish();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctors_login);
        btn_login = (AppCompatButton)findViewById(R.id.btn_login1);

        et_email = (EditText)findViewById(R.id.et_email1);
        et_password = (EditText)findViewById(R.id.et_password1);

        progress = (ProgressBar)findViewById(R.id.progress1);
        pref2 = getApplicationContext().getSharedPreferences("MyPref2",MODE_PRIVATE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    loginProcess(email,password);
                    btn_login.setEnabled(false);

                } else {
                    Toast.makeText(DoctorsLoginActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }




    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants_hospital.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants_hospital.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);


        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(DoctorsLoginActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                if(resp.getResult().equals(Constants_hospital.SUCCESS)){
                    SharedPreferences.Editor editor = pref2.edit();
                    editor.putBoolean(Constants_hospital.IS_LOGGED_IN,true);
                    editor.putString(Constants_hospital.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants_hospital.NAME,resp.getUser().getName());
                    editor.putString(Constants_hospital.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();

                }  else{
                    btn_login.setEnabled(true);
                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
                Toast.makeText(DoctorsLoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void goToProfile(){

        startActivity(new Intent(DoctorsLoginActivity.this,HospitalDashboardActivity.class));
    }
}

