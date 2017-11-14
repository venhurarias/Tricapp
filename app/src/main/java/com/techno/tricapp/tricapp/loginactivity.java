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
import android.widget.TextView;
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

/**
 * Created by arias family on 21/10/2017.
 */

public class loginactivity extends AppCompatActivity

{
    private TextView signup;
    private Button login;
    private EditText usernameinput, passwordinput;
    private RequestQueue requestQueue;
    //private static final String URL = "http://192.168.254.105/triapp/login.php";
    private StringRequest request;

    @Override
    public void onBackPressed() {
        Intent j = new Intent(loginactivity.this,firstactivity.class);
        startActivity(j);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.login_activity);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/login.php";
        signup = (TextView) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login) ;
        usernameinput =(EditText) findViewById(R.id.usernameinput);
        passwordinput = (EditText) findViewById(R.id.passwordinput);

        requestQueue= Volley.newRequestQueue(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.names().get(0).equals("success")){
                                Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(loginactivity.this,driverpageactivity.class);
                                // i.putExtra("firstname",jsonObject.getString("firstname"));
                                // i.putExtra("lastname",jsonObject.getString("lastname"));
                                // i.putExtra("id",jsonObject.getString("id"));
                                //i.putExtra("username",jsonObject.getString("username"));
                                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(loginactivity.this);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("firstname",jsonObject.getString("firstname"));
                                editor.putString("lastname",jsonObject.getString("lastname"));
                                editor.putString("id",jsonObject.getString("id"));
                                editor.putString("username",jsonObject.getString("username"));
                                editor.putString("available",jsonObject.getString("available"));
                                editor.apply();

                                startActivity(i);

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
                        hashMap.put("username",usernameinput.getText().toString());
                        hashMap.put("password",passwordinput.getText().toString());
                        return hashMap;
                    }
                };
                requestQueue.add(request);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signupactivity.class));
            }
        });
    }
}
