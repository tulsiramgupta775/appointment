package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
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

import static com.example.anuj.appointmentrequest.Constants.EMAIL;

public class AccountActivity extends AppCompatActivity {
    android.support.v7.widget.Toolbar toolbar;
    SharedPreferences pref;
    TextView t1,t2;
    private TextView tv_message;
    private EditText et_old_password, et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
         t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);


        final ActionBar actionBar = getSupportActionBar();

        View view = LayoutInflater.from(this).inflate(R.layout.app_bar, null);


        if (actionBar != null) {
            // Disable the default and enable the custom
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            final View customView = getLayoutInflater().inflate(R.layout.actionbar_title, null);

            // Get the textview of the title
            TextView customTitle = (TextView) customView.findViewById(R.id.actionbarTitle);
             TextView customTitle1 = (TextView) customView.findViewById(R.id.actionbartitle2);
            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            customTitle1.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title
            actionBar.setDisplayHomeAsUpEnabled(true);

            // Apply the custom view
            actionBar.setCustomView(customView);
        }
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this,ProfileActivity.class));
            }
        });
        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AccountActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                view = inflater.inflate(R.layout.dialog_change_password, null);
                et_old_password = (EditText)view.findViewById(R.id.et_old_password);
                et_new_password = (EditText)view.findViewById(R.id.et_new_password);
                tv_message = (TextView)view.findViewById(R.id.tv_message);
                progress = (ProgressBar)view.findViewById(R.id.progress);
                builder.setView(view);
                builder.setTitle("Change Password");
                builder.setPositiveButton("Change Password", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog = builder.create();
                dialog.show();
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String old_password = et_old_password.getText().toString();
                        String new_password = et_new_password.getText().toString();
                        if(!old_password.isEmpty() && !new_password.isEmpty()){

                            progress.setVisibility(View.VISIBLE);
                            changePasswordProcess(pref.getString(EMAIL,""),old_password,new_password);

                        }else {

                            tv_message.setVisibility(View.VISIBLE);
                            tv_message.setText("Fields are empty");
                        }
                    }
                });


            }

        });}
    private void changePasswordProcess(String email,String old_password,String new_password){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setOld_password(old_password);
        user.setNew_password(new_password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.CHANGE_PASSWORD_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getResult().equals(Constants.SUCCESS)){
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.GONE);
                    dialog.dismiss();
                    Toast.makeText(AccountActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {


                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }




    }

