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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/**
 * Created by arias family on 21/10/2017.
 */

public class destinationactivity extends AppCompatActivity

{
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button requestride;
    private EditText noteinput;
    private RequestQueue requestQueue;
    private TextView priceshow;
    private TextView placeshow;
    //private static final String URL = "http://192.168.254.105/triapp/ride.php";

    private StringRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.destination_activity);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        final String URL = "http://"+preferences.getString("ip", "")+"/triapp/ride.php";


        addListenerOnButton();

    }

    public void addListenerOnButton() {

        radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
        requestride = (Button) findViewById(R.id.requestride);
        noteinput = (EditText) findViewById(R.id.noteinput);
        priceshow =(TextView) findViewById(R.id.priceshow);
        placeshow =(TextView) findViewById(R.id.placeshow);
        requestQueue= Volley.newRequestQueue(this);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int selectedId = radioGroup.getCheckedRadioButtonId();


                radioButton = (RadioButton) findViewById(selectedId);
                if (radioButton.getText().toString().equals("Mercury")
                        ||radioButton.getText().toString().equals("Saturn")
                        ||radioButton.getText().toString().equals("Jupiter")
                        ||radioButton.getText().toString().equals("Venus")
                        ||radioButton.getText().toString().equals("Earth")) {
                    priceshow.setText("Price: Php 25.00");
                 priceshow.setVisibility(View.VISIBLE);

                }else if(radioButton.getText().toString().equals("Neptune")
                        ||radioButton.getText().toString().equals("Mars")
                        ||radioButton.getText().toString().equals("Galaxy")){
                    priceshow.setText("Price: Php 25.00");
                    priceshow.setVisibility(View.VISIBLE);
                }else if(radioButton.getText().toString().equals("Pluto")
                        ||radioButton.getText().toString().equals("Uranus")
                        ||radioButton.getText().toString().equals("Star")) {
                    priceshow.setText("Price: Php 30.00");
                    priceshow.setVisibility(View.VISIBLE);
                }else{
                    priceshow.setText("Price: Php 30.00");
                    priceshow.setVisibility(View.VISIBLE);
                }
                placeshow.setText(radioButton.getText().toString());
            }
        });
        requestride.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                int selectedId = radioGroup.getCheckedRadioButtonId();


                radioButton = (RadioButton) findViewById(selectedId);
                Intent i = new Intent(destinationactivity.this,rideconfirmationactivity.class);
                i.putExtra("place",radioButton.getText().toString());
                i.putExtra("note",noteinput.getText().toString());
                i.putExtra("driver","any");
                startActivity(i);


            }

        });

    }
}



