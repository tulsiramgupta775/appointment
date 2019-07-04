package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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

import static android.app.PendingIntent.getActivity;
import static com.example.anuj.appointmentrequest.Constants.EMAIL;
import static com.example.anuj.appointmentrequest.Constants.NAME;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener
{
    private TextView tv_message;
    private SharedPreferences pref;
    Button b1,b2,b3;

    private EditText et_old_password,et_new_password;
    private AlertDialog dialog;
    private ProgressBar progress;
    private DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        pref = PreferenceManager.getDefaultSharedPreferences(this);




         b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        b3=(Button) findViewById(R.id.b3);

         b1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(DashboardActivity.this,DoctorsActivity.class));
      }
});





        toolbar=(android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
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
            final TextView customTitle1=(TextView)customView.findViewById(R.id.actionbartitle2);
            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            customTitle1.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title



            // Apply the custom view
            actionBar.setCustomView(customView);
        }





        mDrawerLayout=(DrawerLayout)findViewById(R.id.nav_drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.nav_drawer_open,R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);

        mToggle.setDrawerArrowDrawable(new HamburgerDrawable(this));

        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);


        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);
        TextView name = (TextView) headerView.findViewById(R.id.name);
        name.setText("Welcome : " + pref.getString(Constants.NAME, ""));
        TextView textView = (TextView) headerView.findViewById(R.id.textView);
        textView.setText(pref.getString(Constants.EMAIL, ""));







    }



    private void goToLogin(){

        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
    }








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
                    Toast.makeText(DashboardActivity.this, resp.getMessage(), Toast.LENGTH_SHORT).show();
                }else {
                    progress.setVisibility(View.GONE);
                    tv_message.setVisibility(View.VISIBLE);
                    tv_message.setText(resp.getMessage());

                }
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

//                Log.d(Constants.TAG,"failed");
                progress.setVisibility(View.GONE);
                tv_message.setVisibility(View.VISIBLE);
                tv_message.setText(t.getLocalizedMessage());


            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.action_password){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.dialog_change_password, null);
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
        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.adr) {


        } if(id==R.id.appointment) {

        }

        if(id==R.id.help) {

        } if(id==R.id.rate) {

            try{
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market:/details?id=com.whatsapp&hl=en")));
            }catch (Exception e){
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")));

            }
        } if(id==R.id.share) {


        }if(id==R.id.about) {

        }
        if(id==R.id.log) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.IS_LOGGED_IN,false);
            editor.putString(EMAIL,"");
            editor.putString(NAME,"");
            editor.putString(Constants.UNIQUE_ID,"");
            editor.apply();
            goToLogin();
        }
        if(id==R.id.about_version) {

        }return false;
    }
    }

