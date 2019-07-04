package com.example.anuj.appointmentrequest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anuj.appointmentrequest.models.ServerRequest;
import com.example.anuj.appointmentrequest.models.ServerResponse;
import com.example.anuj.appointmentrequest .models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton btn_register;
    private EditText et_email,et_password,et_name;
    private TextView tv_login;
    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btn_register = (AppCompatButton)findViewById(R.id.btn_register);
        tv_login = (TextView)findViewById(R.id.tv_login);
        et_name = (EditText)findViewById(R.id.et_name);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);

        progress = (ProgressBar)findViewById(R.id.progress);

        btn_register.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv_login:
                goToLogin();
                break;

            case R.id.btn_register:

                String name = et_name.getText().toString();
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    registerProcess(name,email,password);

                } else {

                     Toast.makeText(this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }

    private void registerProcess(String name, String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(RegisterActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
                Toast.makeText(RegisterActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void goToLogin(){

        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }
}
