package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AdmitActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener   {
    Calendar date;
    TextView et_date,et_Patient,et_Father,et_Mobile;
     Button pay;
    String currentDateString;
    private Spinner spinner;
    public static final String TAG_USERNAME = "bed_no";
    ArrayList<String> bed;
    private JSONArray result;
    String  User,Patient,Father,Date,Mobile,Bed;
    SharedPreferences pref;
    public static final String JSON_ARRAY = "beds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admit);
        pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);

        spinner = (Spinner) findViewById(R.id.bedspinner);
        getData();

        bed = new ArrayList<String>();
        et_date = (TextView) findViewById(R.id.et_dt);
        et_Patient = (TextView) findViewById(R.id.et_Patient);
        et_Father = (TextView) findViewById(R.id.et_Father);
        et_Mobile = (TextView) findViewById(R.id.et_Mobile);
        pay=(Button)findViewById(R.id.btn_pay);
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar currentDate = Calendar.getInstance();
                date = Calendar.getInstance();


                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {

                        date.set(Calendar.YEAR, year);
                        date.set(Calendar.MONTH, monthOfYear);
                        date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(date.getTime());
                        et_date.setText(currentDateString);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(AdmitActivity.this, dateSetListener, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

                datePickerDialog.show();


            }

        });

pay.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Bed = spinner.getSelectedItem().toString();
        Patient = et_Patient.getText().toString();
        Father = et_Father.getText().toString();
        Date = et_date.getText().toString();
        Mobile = et_Mobile.getText().toString();
        User = pref.getString(Constants.NAME, "");
        if(!Patient.isEmpty() && !Father.isEmpty()&&!Date.isEmpty() && !Mobile.isEmpty()) {

            AdmitActivity.BackgroundProcess backgroundProcess = new AdmitActivity.BackgroundProcess(AdmitActivity.this);
            backgroundProcess.execute(Patient,Father,Date,Mobile,User,Bed);
            finish();

        } else {
            Toast.makeText(AdmitActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
        }


    }
});
    }


    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("http://anujagrawalpm.000webhostapp.com/appointment/BedAvailibility.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getBeds(result);
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

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }


    private void getBeds(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                bed.add(json.getString(TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(AdmitActivity.this, android.R.layout.simple_spinner_dropdown_item,bed ));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    public class BackgroundProcess extends AsyncTask<String, Void, String> {
            Context context;

            BackgroundProcess(Context ctx) {
                context = ctx;
            }

            AlertDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = new AlertDialog.Builder(context).create();
                loading.setTitle("uploading....");
            }

            @Override
            protected String doInBackground(String... params) {

                String upload_url = "http://anujagrawalpm.000webhostapp.com/appointment/admit.php";

                String Patient = params[0];
                String Father = params[1];
                String Date = params[2];
                String Mobile = params[3];
                String User = params[4];

                String Bed = params[5];
                try {
                    URL url = new URL(upload_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Patient", "UTF-8") + "=" + URLEncoder.encode(Patient, "UTF-8") + "&" +
                            URLEncoder.encode("Father", "UTF-8") + "=" + URLEncoder.encode(Father, "UTF-8") + "&" +
                            URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8") + "&" +
                            URLEncoder.encode("Mobile", "UTF-8") + "=" + URLEncoder.encode(Mobile, "UTF-8") + "&" +
                            URLEncoder.encode("User", "UTF-8") + "=" + URLEncoder.encode(User, "UTF-8") + "&" +
                            URLEncoder.encode("Bed", "UTF-8") + "=" + URLEncoder.encode(Bed, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    //httpURLConnection.connect();
                    httpURLConnection.disconnect();
                    return "Uploaded...";
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (result.equals("Uploaded...")) {
                    Toast.makeText(context, result, Toast.LENGTH_LONG).show();
                } else {
                    loading.setMessage(result);
                    loading.show();

                }
                // Toast.makeText(context, result, Toast.LENGTH_LONG).show();

            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
            }
        }
    }
