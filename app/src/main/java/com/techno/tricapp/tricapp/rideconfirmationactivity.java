package com.techno.tricapp.tricapp;

/**
 * Created by arias on 11/14/2017.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;



public class rideconfirmationactivity extends AppCompatActivity {
    private RequestQueue requestQueue;
    //private static final String URL = "http://192.168.254.105/triapp/ride.php";
    private StringRequest request;
    private Button yesbutton, gobackbutton;
    private String price="";

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.rideconfirmation);

        if (getIntent().getStringExtra("place").equals("Mercury")
                ||getIntent().getStringExtra("place").equals("Saturn")
                ||getIntent().getStringExtra("place").equals("Jupiter")
                ||getIntent().getStringExtra("place").equals("Venus")
                ||getIntent().getStringExtra("place").equals("Earth")) {
            price="Php 25.00";
        }else if(getIntent().getStringExtra("place").equals("Neptune")
                ||getIntent().getStringExtra("place").equals("Mars")
                ||getIntent().getStringExtra("place").equals("Galaxy")){
            price="Php 25.00";
        }else if(getIntent().getStringExtra("place").equals("Pluto")
                ||getIntent().getStringExtra("place").equals("Uranus")
                ||getIntent().getStringExtra("place").equals("Star")) {
            price = "Php 30.00";
        }else{
            price = "Php 30.00";
        }
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/ride.php";

        yesbutton=(Button) findViewById(R.id.yesbutton);
        gobackbutton=(Button) findViewById(R.id.gobackbutton);

        requestQueue= Volley.newRequestQueue(this);
        gobackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(rideconfirmationactivity.this, destinationactivity.class);
                startActivity(in);
            }
        });
        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),firstactivity.class));
                            }else{
                                Toast.makeText(getApplicationContext(),"ERROR "+jsonObject.getString("error"),Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("place", getIntent().getStringExtra("place"));
                        hashMap.put("note",getIntent().getStringExtra("note"));
                        hashMap.put("driver",getIntent().getStringExtra("driver"));
                        hashMap.put("price",price);

                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });

    }
}
