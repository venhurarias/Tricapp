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

/**
 * Created by arias on 11/14/2017.
 */

public class demoactivity extends AppCompatActivity {
    private Button startbtn;
    private EditText ipinput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.demoactivity);

        startbtn =(Button) findViewById(R.id.startbtn);
        ipinput =(EditText) findViewById(R.id.ipinput);

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(demoactivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("ip",ipinput.getText().toString());
                editor.apply();
                startActivity(new Intent(getApplicationContext(),startupactivity.class));
            }
        });

    }
}
