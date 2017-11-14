package com.techno.tricapp.tricapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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


public class signupactivity extends AppCompatActivity

{
    private EditText usernamesign, passwordsign, confirmpasssign, firstnamesign, lastnamesign, platenumbersign, regsign, addresssign;
    private Button signupbutton;
    private RequestQueue requestQueue;
    //private static final String URL = "http://192.168.254.104/triapp/signup.php";
    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.signup_activity);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/signup.php";
        usernamesign =(EditText) findViewById(R.id.usernamesign);
        passwordsign = (EditText) findViewById(R.id.passwordsign);
        confirmpasssign =(EditText) findViewById(R.id.confirmpasssign);
        firstnamesign = (EditText) findViewById(R.id.firstnamesign);
        lastnamesign = (EditText) findViewById(R.id.lastnamesign);
        platenumbersign = (EditText) findViewById(R.id.platenumbersign);
        signupbutton = (Button) findViewById (R.id.signupbutton);
        regsign = (EditText) findViewById (R.id.regsign);
        addresssign = (EditText) findViewById (R.id.addresssign);

        requestQueue= Volley.newRequestQueue(this);

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(passwordsign.getText().toString().equals(confirmpasssign.getText().toString()))
                {
                    request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if(jsonObject.names().get(0).equals("success")){
                                    Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(),destinationactivity.class));
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
                            hashMap.put("username",usernamesign.getText().toString());
                            hashMap.put("password",passwordsign.getText().toString());
                            hashMap.put("firstname",firstnamesign.getText().toString());
                            hashMap.put("lastname",lastnamesign.getText().toString());
                            hashMap.put("platenumber",platenumbersign.getText().toString());
                            hashMap.put("reg",regsign.getText().toString());
                            hashMap.put("address",addresssign.getText().toString());
                            return hashMap;
                        }
                    };
                    requestQueue.add(request);

                }else{
                    Toast.makeText(getApplicationContext(),"password doesnot match",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}

