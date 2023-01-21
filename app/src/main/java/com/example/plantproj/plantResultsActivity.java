package com.example.plantproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class plantResultsActivity extends AppCompatActivity {

    public static String plantInfo;
    public static String plantWiki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantresult_layout);

        plantInfo = MLActivity.plantResults.get(0);
        plantWiki= Backend.wikiFinal;

        // String plantWiki = MLActivity.W
        configureResults(plantInfo);
        configureWiki(plantWiki);

        configureNextButton();

        MLActivity.plantResults.clear();
        Backend.plantWikis.clear();


    }


    private void configureResults(String plantinfo) {
        TextView resultText = (TextView) findViewById(R.id.resultBox);
        resultText.setText("Plant Name: " + plantinfo);
    }

    private void configureWiki(String wikiinfo) {
        TextView resultWiki = (TextView) findViewById(R.id.wikiBox);
        resultWiki.setText("Get more info about your find here" + wikiinfo);
    }



    private void configureNextButton() {
        Button nextButton = (Button) findViewById(R.id.carePlantButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(plantResultsActivity.this, MainActivity.class));
            }
        });
    } // configureNextButtion


}
