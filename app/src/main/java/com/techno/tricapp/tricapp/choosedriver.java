package com.techno.tricapp.tricapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.amigold.fundapter.BindDictionary;
import com.amigold.fundapter.FunDapter;
import com.amigold.fundapter.extractors.StringExtractor;
import com.amigold.fundapter.interfaces.DynamicImageLoader;
import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

/**
 * Created by arias on 11/15/2017.
 */

public class choosedriver extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {
    private ArrayList<showdriver> ridelist;
    private ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.choose_driver);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/showdriver.php";

        PostResponseAsyncTask taskread= new PostResponseAsyncTask(choosedriver.this, this);
        //taskread.execute("http://192.168.254.105/triapp/showride.php");
        taskread.execute(URL);

    }
    @Override
    public void processFinish(String s) {

        //Toast.makeText(driverpageactivity.this, s, Toast.LENGTH_SHORT).show();
        ridelist = new JsonConverter<showdriver>().toArrayList(s, showdriver.class);
        BindDictionary<showdriver> dict = new BindDictionary<showdriver>();
        dict.addStringField(R.id.usernamedr, new StringExtractor<showdriver>() {
            @Override
            public String getStringValue(showdriver driver, int position) {
                return driver.username;
            }
        });
        dict.addStringField(R.id.firstnamedr, new StringExtractor<showdriver>() {
            @Override
            public String getStringValue(showdriver driver, int position) {
                return driver.firstname;
            }
        });
        dict.addStringField(R.id.lastnamedr, new StringExtractor<showdriver>() {
            @Override
            public String getStringValue(showdriver driver, int position) {
                return driver.lastname;
            }
        });
        dict.addStringField(R.id.platenumberdr, new StringExtractor<showdriver>() {
            @Override
            public String getStringValue(showdriver driver, int position) {
                return driver.platenumber;
            }
        });
        dict.addStringField(R.id.addressdr, new StringExtractor<showdriver>() {
            @Override
            public String getStringValue(showdriver driver, int position) {
                return driver.address;
            }
        });


        final FunDapter<showdriver> adapter = new FunDapter<>(
                choosedriver.this, ridelist, R.layout.formatdriver, dict
        );



        list =(ListView)findViewById(R.id.chooselist);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        showdriver selecteddriver = ridelist.get(position);
        Intent in = new Intent(choosedriver.this, rideconfirmationactivity.class);
        in.putExtra("driver", selecteddriver.username);
        in.putExtra("place", getIntent().getStringExtra("place"));
        in.putExtra("note", getIntent().getStringExtra("note"));


        startActivity(in);
    }
}
