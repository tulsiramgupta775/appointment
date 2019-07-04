package com.example.anuj.appointmentrequest;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
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

public class SlotUploadActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    EditText et_day,et_daytime, et_from_time, et_to_time;
    private Spinner spinner;

    ArrayList<String> doctors_List;
    private JSONArray result;
    Toolbar toolbar;
    private Button UploadSlot;
    String day,daytime,from_time,to_time,docname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slot_upload);


        doctors_List = new ArrayList<String>();


        //Initializing Spinner
        spinner = (Spinner) findViewById(R.id.docspinner);

        //Adding an Item Selected Listener to our Spinner
        //As we have implemented the class Spinner.OnItemSelectedListener to this class iteself we are passing this to setOnItemSelectedListener


        getData();


        et_from_time = (EditText) findViewById(R.id.from_time);
        et_to_time = (EditText) findViewById(R.id.to_time);
        et_day=(EditText)findViewById(R.id.day);
        et_daytime=(EditText)findViewById(R.id.daytime);


        UploadSlot = (Button) findViewById(R.id.Upload);

        //checking the permission
        //if the permission is not given we will open setting to add permission
        //else app will not open


        toolbar = (Toolbar) findViewById(R.id.toolbar);
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
            final TextView customTitle1 = (TextView) customView.findViewById(R.id.actionbartitle2);
            // Change the font family (optional)
            customTitle.setTypeface(Typeface.MONOSPACE);
            customTitle1.setTypeface(Typeface.MONOSPACE);
            // Set the on click listener for the title

            actionBar.setDisplayHomeAsUpEnabled(true);
            // Apply the custom view
            actionBar.setCustomView(customView);
        }


        //adding click listener to button
        UploadSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                from_time =et_from_time.getText().toString();
               to_time = et_to_time.getText().toString();
                day = et_day.getText().toString();
                daytime = et_daytime.getText().toString();
                docname=spinner.getSelectedItem().toString();

                if(!from_time.isEmpty() && !to_time.isEmpty()&&!day.isEmpty() && !daytime.isEmpty()&& !docname.isEmpty()) {

                    BackgroundProcess backgroundProcess = new BackgroundProcess(SlotUploadActivity.this);
                    backgroundProcess.execute(docname,day,daytime,from_time,to_time);
                    finish();

                } else {
                    Toast.makeText(SlotUploadActivity.this, "Fields are empty !", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }


    public class BackgroundProcess extends AsyncTask<String,Void,String> {
        Context context;

        BackgroundProcess (Context ctx) {
            context = ctx;
        }
        AlertDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new AlertDialog.Builder(context).create();
            loading.setTitle("uploading....");  }
        @Override
        protected String doInBackground(String... params) {

            String upload_url = "http://anujagrawalpm.000webhostapp.com/appointment/slot_details.php?apicall=uploadslot";
            String docname = params[0];
            String day = params[1];
            String daytime = params[2];
                String from_time = params[3];
                String to_time = params[4];
            try {
                URL url = new URL(upload_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("docname", "UTF-8") + "=" + URLEncoder.encode(docname, "UTF-8") + "&" +
                        URLEncoder.encode("day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8") + "&" +
                        URLEncoder.encode("daytime", "UTF-8") + "=" + URLEncoder.encode(daytime, "UTF-8")+ "&" +
                        URLEncoder.encode("from_time", "UTF-8") + "=" + URLEncoder.encode(from_time, "UTF-8")+ "&" +
                        URLEncoder.encode("to_time", "UTF-8") + "=" + URLEncoder.encode(to_time, "UTF-8");
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
            if(result.equals("Uploaded..."))
            {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
            else
            {    loading.setMessage(result);
                loading.show();

            }
            // Toast.makeText(context, result, Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }



    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Config.DATA_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getdoctors_list(result);
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


    private void getdoctors_list(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                doctors_List.add(json.getString(Config.TAG_USERNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        spinner.setAdapter(new ArrayAdapter<String>(SlotUploadActivity.this, android.R.layout.simple_spinner_dropdown_item, doctors_List));
    }


        @Override
        public void onItemSelected (AdapterView < ? > adapterView, View view,int i, long l){

        }

        @Override
        public void onNothingSelected (AdapterView < ? > adapterView){

        }
    }




