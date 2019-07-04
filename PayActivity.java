package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.ArrayList;

public class PayActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    String docname, fees, from_time, to_time, day, date, user;
    String Patient, Doctor, User, Date, Day, Time, Fees;
    TextView t_Doctor, t_Day, t_Date, t_Time, t_Fees, t_User;
    EditText et_patient;
    Button pay;
    private Spinner spinner;
    public static final String TAG_USERNAME = "Patient";
    ArrayList<String> patient;
    private JSONArray result;

    public static final String JSON_ARRAY = "admit";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        spinner = (Spinner) findViewById(R.id.patientspinner);
        getData();
        patient = new ArrayList<String>();
        day = getIntent().getStringExtra("Day");
        from_time = getIntent().getStringExtra("from_time");
        to_time = getIntent().getStringExtra("to_time");
        fees = getIntent().getStringExtra("Fees");
        user = getIntent().getStringExtra("User");
        docname = getIntent().getStringExtra("Docname");
        date = getIntent().getStringExtra("Date");

        t_Doctor = (TextView) findViewById(R.id.te7);
        t_Day = (TextView) findViewById(R.id.te9);
        t_Date = (TextView) findViewById(R.id.te8);
        t_Time = (TextView) findViewById(R.id.te10);
        t_Fees = (TextView) findViewById(R.id.te11);
        t_User = (TextView) findViewById(R.id.te15);
        t_Doctor.setText(docname);
        t_User.setText(user);
        t_Day.setText(day);
        t_Date.setText(date);
        t_Time.setText(from_time + "-" + to_time);
        t_Fees.setText(fees);
        pay = (Button) findViewById(R.id.btn1);
        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Patient = spinner.getSelectedItem().toString();
                Doctor = t_Doctor.getText().toString();
                User = t_User.getText().toString();
                Date = t_Date.getText().toString();
                Day = t_Day.getText().toString();
                Time = t_Time.getText().toString();
                Fees = t_Fees.getText().toString();


                if(!Patient.isEmpty()) {

                    PayActivity.BackgroundProcess backgroundProcess = new PayActivity.BackgroundProcess(PayActivity.this);
                    backgroundProcess.execute(Patient, Doctor, User, Date, Day, Time, Fees);
                    finish();

                } else {
                    Toast.makeText(PayActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }




            }
        });}
    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest("http://anujagrawalpm.000webhostapp.com/appointment/getPatient.php",
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
                            getPatient(result);
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


    private void getPatient(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                patient.add(json.getString(TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(PayActivity.this, android.R.layout.simple_spinner_dropdown_item, patient));
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

                String upload_url = "http://anujagrawalpm.000webhostapp.com/appointment/Booking.php?apicall=uploadpatient";

                String Patient = params[0];
                String Doctor = params[1];
                String User = params[2];
                String Date = params[3];
                String Day = params[4];
                String Time = params[5];
                String Fees = params[6];
                try {
                    URL url = new URL(upload_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Patient", "UTF-8") + "=" + URLEncoder.encode(Patient, "UTF-8") + "&" +
                            URLEncoder.encode("Doctor", "UTF-8") + "=" + URLEncoder.encode(Doctor, "UTF-8") + "&" +
                            URLEncoder.encode("User", "UTF-8") + "=" + URLEncoder.encode(User, "UTF-8") + "&" +
                            URLEncoder.encode("Date", "UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8") + "&" +
                            URLEncoder.encode("Day", "UTF-8") + "=" + URLEncoder.encode(Day, "UTF-8") + "&" +
                            URLEncoder.encode("Time", "UTF-8") + "=" + URLEncoder.encode(Time, "UTF-8") + "&" +
                            URLEncoder.encode("Fees", "UTF-8") + "=" + URLEncoder.encode(Fees, "UTF-8");
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
                    Intent i = new Intent(context,BackToHomeActivity.class);
                    context.startActivity(i);

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


