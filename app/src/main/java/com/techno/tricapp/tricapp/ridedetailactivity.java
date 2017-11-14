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
import android.widget.TextView;

import org.w3c.dom.Text;



public class ridedetailactivity extends AppCompatActivity {
    TextView placedetail, notedetail, timedetail, statusdetail, pricedetail, tv;
    Button acceptdetail;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.ridedetail);


        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        placedetail = (TextView) findViewById(R.id.placedetail);
        notedetail=(TextView) findViewById(R.id.notedetail);
        timedetail=(TextView) findViewById(R.id.timedetail);
        statusdetail=(TextView)findViewById(R.id.statusdetail);
        acceptdetail=(Button) findViewById(R.id.acceptdetail) ;
        pricedetail=(TextView) findViewById(R.id.pricedetail);
        tv=(TextView)findViewById(R.id.tv);



        username=preferences.getString("username", "");

        placedetail.setText(getIntent().getStringExtra("place"));
        notedetail.setText(getIntent().getStringExtra("note"));
        timedetail.setText(getIntent().getStringExtra("time"));
        statusdetail.setText(getIntent().getStringExtra("status"));
        pricedetail.setText(getIntent().getStringExtra("price"));

        if (getIntent().getStringExtra("status").equals("Done"))
        {
            tv.setVisibility(View.GONE);
            acceptdetail.setVisibility(View.GONE);

        }else{
            tv.setVisibility(View.VISIBLE);
            acceptdetail.setVisibility(View.VISIBLE);
        }


        acceptdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(ridedetailactivity.this, driverconfirmactivity.class);
                in.putExtra("place", getIntent().getStringExtra("place"));
                in.putExtra("note", getIntent().getStringExtra("note"));
                in.putExtra("time", getIntent().getStringExtra("time"));
                in.putExtra("status", getIntent().getStringExtra("status"));
                in.putExtra("rideid", getIntent().getStringExtra("rideid"));
                in.putExtra("price", getIntent().getStringExtra("price"));
                in.putExtra("username",username);
                startActivity(in);
            }
        });




    }
}
