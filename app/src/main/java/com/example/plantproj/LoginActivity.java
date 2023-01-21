package com.example.plantproj;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    public static List<String> plantDatabase = new ArrayList<String>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        configureNextButton();
        configureInfo();

        // will be used for area
        plantDatabase.add("Dracena");
        plantDatabase.add("Monstera Deliciosa");
        plantDatabase.add("Hectors Spinefiled");
        plantDatabase.add("Red Marram");
        plantDatabase.add("English Vine");



    }




    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.loginbutton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, MapsActivity.class));
            }
        });
    } // configureNextButtion


    private void configureInfo() {
        Button infopButton = (Button) findViewById(R.id.infopointsbutton);
        infopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, pointsActivity.class));
            }
        });
    } // configureNextButtion




}