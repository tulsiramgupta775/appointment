package com.example.anuj.appointmentrequest;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.anuj.appointmentrequest.models.ServerRequest;
import com.example.anuj.appointmentrequest.models.ServerResponse;
import com.example.anuj.appointmentrequest.models.User;
import com.example.anuj.appointmentrequest.models.doctors;
import com.example.anuj.appointmentrequest.models.doctorsAdapter;
import com.example.anuj.appointmentrequest.models.tips;
import com.example.anuj.appointmentrequest.models.tipsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.PendingIntent.getActivity;
import static com.example.anuj.appointmentrequest.Constants.EMAIL;
import static com.example.anuj.appointmentrequest.Constants.NAME;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tv_message;
    private SharedPreferences pref;
    Button b1, b2, b3,b4;


    private DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    private NavigationView mNavigationView;
    private ActionBarDrawerToggle mToggle;

    private static final String URL_tips = "http://anujagrawalpm.000webhostapp.com/appointment/tipslist.php?apicall=getpics";

    //a list to store all the products
    List<tips> tips_List;

    //the recyclerview
    RecyclerView recyclerViewHealth;
    @Override
    public void onBackPressed() {
        startActivity(new Intent(DashboardActivity.this,HomeActivity.class));
        finish();}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        pref = getApplicationContext().getSharedPreferences("MyPref",MODE_PRIVATE);


        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        b3 = (Button) findViewById(R.id.b3);
        b4=(Button)findViewById(R.id.b4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, DoctorsActivity.class));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, AccountActivity.class));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
             startActivity(new Intent(DashboardActivity.this,AdmitActivity.class));
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:9166971455"));
                callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (ActivityCompat.checkSelfPermission(DashboardActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);
            }
        });
        recyclerViewHealth = findViewById(R.id.recylcerViewHealth);
        recyclerViewHealth.setHasFixedSize(true);


        //initializing the productlist
        tips_List = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview
        loadproducts();

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewHealth.setLayoutManager(mLayoutManager);
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final ActionBar actionBar = getSupportActionBar();

        View view = LayoutInflater.from(this).inflate(R.layout.app_bar_dashboard, null);


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



        mDrawerLayout=(DrawerLayout)findViewById(R.id.nav_drawer);
        mToggle=new ActionBarDrawerToggle(this,mDrawerLayout,R.string.nav_drawer_open,R.string.nav_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);


        mToggle.setDrawerArrowDrawable(new HamburgerDrawable(this));

        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);


        mNavigationView.setNavigationItemSelectedListener(this);
        View headerView = mNavigationView.getHeaderView(0);






    }



    private void goToLogin(){

        startActivity(new Intent(DashboardActivity.this,HomeActivity.class));
        finish();
    }


    private void loadproducts() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_tips,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the string to json array object
                            JSONObject object = new JSONObject(response);
                            JSONArray array  = object.getJSONArray("tipslist");
                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {

                                //getting product object from json array
                                JSONObject tips = array.getJSONObject(i);

                                //adding the product to product list
                                tips_List.add(new tips(
                                        tips.getInt("id"),
                                        tips.getString("tipscategory"),
                                        tips.getString("image"),
                                        tips.getString("tips")

                                ));
                            }

                            //creating adapter object and setting it to recyclerview
                            tipsAdapter adapter = new tipsAdapter(DashboardActivity.this, tips_List);
                            recyclerViewHealth.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        //adding our stringrequest to queue
        Volley.newRequestQueue(this).add(stringRequest);
    }






    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.menu_toolbar,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {




        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        }
        return super.onOptionsItemSelected(item);


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        if(id==R.id.adr) {
            mDrawerLayout.closeDrawer(Gravity.START, false);

        } if(id==R.id.appointment) {
            startActivity(new Intent(DashboardActivity.this,AppointmentsActivity.class));
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }

        if(id==R.id.help) {
            mDrawerLayout.closeDrawer(Gravity.START, false);
        } if(id==R.id.rate) {

            try{
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market:/details?id=com.whatsapp&hl=en")));
            }catch (Exception e){
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=com.whatsapp&hl=en")));

            } mDrawerLayout.closeDrawer(Gravity.START, false);
        } if(id==R.id.share) {

            mDrawerLayout.closeDrawer(Gravity.START, false);
        }if(id==R.id.about) {
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }
        if(id==R.id.log) {

            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(Constants.IS_LOGGED_IN,false);
            editor.putString(EMAIL,"");
            editor.putString(NAME,"");

            editor.putString(Constants.UNIQUE_ID,"");
            editor.apply();
            goToLogin();
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }
        if(id==R.id.about_version) {
            mDrawerLayout.closeDrawer(Gravity.START, false);
        }return false;
    }
    }

