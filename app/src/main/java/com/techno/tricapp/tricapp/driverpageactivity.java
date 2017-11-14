package com.techno.tricapp.tricapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arias on 11/14/2017.
 */

public class driverpageactivity extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener

{

    private String firstname;
    private String lastname;
    private TextView drivername;
    private String id;
    private ArrayList<showride> ridelist;
    private ListView list;
    private String usernamereset, firstnamereset, lastnamereset, idreset;
    private ImageView ivstatus;
    private Button logout, availablebtn, refreshbtn;
    final Handler handler = new Handler();
    private StringRequest request;
    private RequestQueue requestQueue;

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.driverpage);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        ivstatus=(ImageView) findViewById(R.id.ivstatus);
        drivername = (TextView) findViewById(R.id.drivername);
        availablebtn=(Button) findViewById(R.id.availablebtn);
        refreshbtn=(Button) findViewById(R.id.refreshbtn);
        firstname=preferences.getString("firstname", "");
        lastname=preferences.getString("lastname", "");
        id=preferences.getString("id", "");
        SharedPreferences.Editor editor = preferences.edit();
refreshbtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(driverpageactivity.this);
        SharedPreferences.Editor editor = preferences.edit();
        if(availablebtn.getText().toString().equals("set to available")){
            editor.putString("available","no");
        }
        else{
            editor.putString("available","yes");
        }

        editor.apply();

        finish();
        startActivity(getIntent());
        Toast.makeText(getApplicationContext(),"Page Refreshed!",Toast.LENGTH_SHORT).show();
    }
});

        if(preferences.getString("available", "").equals("no"))
        availablebtn.setText("set to available");
        else
            availablebtn.setText("set to unavailable");

        requestQueue= Volley.newRequestQueue(this);
availablebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(driverpageactivity.this);
        final String[] id = {preferences.getString("id", "")};
        final String available=preferences.getString("available", "");
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/updateavail.php";


        request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.names().get(0).equals("success")){
                        Toast.makeText(getApplicationContext(),"SUCCESS "+jsonObject.getString("success"),Toast.LENGTH_SHORT).show();

                        if(availablebtn.getText().toString().equals("set to available")){
                            availablebtn.setText("set to unavailable");
                        }
                        else{
                            availablebtn.setText("set to available");
                        }


                    }else{

                        Toast.makeText(getApplicationContext(),"An error occured",Toast.LENGTH_SHORT).show();

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
                hashMap.put("id", id[0]);
                hashMap.put("available",available);
                return hashMap;
            }
        };
        requestQueue.add(request);
    }
});
        logout=(Button)findViewById(R.id.logout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences1 = PreferenceManager.getDefaultSharedPreferences(driverpageactivity.this);
                SharedPreferences.Editor editor = preferences1.edit();
                editor.putString("username","");
                editor.putString("firstname","");
                editor.putString("lastname","");
                editor.putString("id","");
                startActivity(new Intent(getApplicationContext(),firstactivity.class));
            }
        });
        drivername.setText(firstname+" "+lastname);
        //SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/showride.php";

        PostResponseAsyncTask taskread= new PostResponseAsyncTask(driverpageactivity.this, this);
        //taskread.execute("http://192.168.254.105/triapp/showride.php");
        taskread.execute(URL);


    }


    @Override
    public void processFinish(String s) {

        //Toast.makeText(driverpageactivity.this, s, Toast.LENGTH_SHORT).show();
        ridelist = new JsonConverter<showride>().toArrayList(s, showride.class);
        BindDictionary<showride> dict = new BindDictionary<showride>();
        dict.addStringField(R.id.placelist, new StringExtractor<showride>() {
            @Override
            public String getStringValue(showride ride, int position) {
                return ride.place;
            }
        });

        dict.addStringField(R.id.timelist, new StringExtractor<showride>() {
            @Override
            public String getStringValue(showride ride, int position) {
                return ride.time;
            }
        });

        dict.addStringField(R.id.statuslist, new StringExtractor<showride>() {
            @Override
            public String getStringValue(showride ride, int position) {
                return ride.status;
            }
        });
        dict.addStringField(R.id.pricelist, new StringExtractor<showride>() {
            @Override
            public String getStringValue(showride ride, int position) {
                return ride.price;
            }
        });

        dict.addDynamicImageField(R.id.ivstatus, new StringExtractor<showride>() {
            @Override
            public String getStringValue(showride ride, int position) {
                return ride.status;
            }
        }, new DynamicImageLoader() {
            @Override
            public void loadImage(String url, ImageView imageView) {

                if (url.equals("Done")){
                    imageView .setVisibility(View.GONE);
                }
                else{
                    imageView .setVisibility(View.VISIBLE);

                }


            }
        });
        final FunDapter<showride> adapter = new FunDapter<>(
                driverpageactivity.this, ridelist, R.layout.formatview, dict
        );



        list =(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showride selectedride = ridelist.get(position);
        Intent in = new Intent(driverpageactivity.this, ridedetailactivity.class);
        in.putExtra("place", selectedride.place);
        in.putExtra("note", selectedride.note);
        in.putExtra("time", selectedride.time);
        in.putExtra("status", selectedride.status);
        in.putExtra("rideid", selectedride.rideid);
        in.putExtra("price", selectedride.price);
        //in.putExtra("username",username);
        startActivity(in);
    }
}