package com.techno.tricapp.tricapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by arias on 11/14/2017.
 */

public class driverconfirmactivity extends AppCompatActivity{
    TextView testconfirm;
    Button yesdriver, nodriver;
    private RequestQueue requestQueue;
    //private static final String URL = "http://192.168.254.105/triapp/update.php";
    private StringRequest request;
    String rideid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.driverconfirmation);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/update.php";
        rideid=getIntent().getStringExtra("rideid");
        testconfirm=(TextView) findViewById(R.id.testconfirm);
        yesdriver=(Button) findViewById(R.id.yesdriver);
        nodriver=(Button) findViewById(R.id.nodriver);
        requestQueue= Volley.newRequestQueue(this);
        nodriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        yesdriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),driverpageactivity.class));
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
                        hashMap.put("rideid",rideid);
                        hashMap.put("username",getIntent().getStringExtra("username"));
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });
       // testconfirm.setText(getIntent().getStringExtra("rideid")+getIntent().getStringExtra("note")+getIntent().getStringExtra("time")+getIntent().getStringExtra("status"));


    }
}
