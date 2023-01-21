package com.example.plantproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class plantActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.whatplant_layout);
        configureBackButton();
        configuresearchByPhotoButton();
        Toast uploadPlantToast = Toast.makeText(getApplicationContext(), "Photo is being uploaded", Toast.LENGTH_LONG);
        uploadPlantToast.show();

    }


            private void configureBackButton() {
            Button backButton = (Button) findViewById(R.id.backbutton);
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }


    private void configuresearchByPhotoButton() {
        Button byphotoButton = (Button) findViewById(R.id.search_byphoto);
        byphotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MLActivity.class);
                startActivity(intent);



            }
        });


    }













}













