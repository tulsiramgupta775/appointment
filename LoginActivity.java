package com.example.anuj.appointmentrequest;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anuj.appointmentrequest.models.ServerRequest;
import com.example.anuj.appointmentrequest.models.ServerResponse;
import com.example.anuj.appointmentrequest.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.getActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton btn_login;
    private EditText et_email,et_password;
    private TextView tv_register;
    private ProgressBar progress;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btn_login = (AppCompatButton)findViewById(R.id.btn_login);
        tv_register = (TextView)findViewById(R.id.tv_register);
        et_email = (EditText)findViewById(R.id.et_email);
        et_password = (EditText)findViewById(R.id.et_password);

        progress = (ProgressBar)findViewById(R.id.progress);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        btn_login.setOnClickListener(this);
        tv_register.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_register:
                goToRegister();
                break;

            case R.id.btn_login:
                String email = et_email.getText().toString();
                String password = et_password.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    progress.setVisibility(View.VISIBLE);
                    loginProcess(email,password);

                } else {
                    Toast.makeText(this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                   }
                break;

        }
    }
    private void loginProcess(String email,String password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(LoginActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.EMAIL,resp.getUser().getEmail());
                    editor.putString(Constants.NAME,resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID,resp.getUser().getUnique_id());
                    editor.apply();
                    goToProfile();

                }
                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
//                Log.d(Constants.TAG,"failed");
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void goToRegister(){

        startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
    }

    private void goToProfile(){

        startActivity(new Intent(LoginActivity.this,DashboardActivity.class));
    }
}
