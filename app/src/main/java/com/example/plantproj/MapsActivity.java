package com.example.plantproj;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.multidex.MultiDex;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.Manifest;

import java.lang.*;
import java.sql.*;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String Latitude;
    private LocationManager locationManager;
    double longitude = 0;
    double latitude = 0;
    String provider;
    Button addAPlantButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        configureNextButton();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        provider = locationManager.getBestProvider(new Criteria(), false);


        checkLocationPermission();


        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {

            latitude = location.getLatitude();
            longitude = location.getLongitude();



        } else {

            Log.i("Location Info", "No location :(");

        }

    }

    private void configureNextButton() {
        addAPlantButton = (Button) findViewById(R.id.AddAPlant);
        addAPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MapsActivity.this, plantActivity.class));
            }
        });
    }



    // this returns the distance from one coordinate and another
    public double HaverSineFormula(LatLng coordinateOne, LatLng coordinateTwo){
        final int R = 6371;
        double latitudeOne = coordinateOne.latitude;
        double longitudeOne = coordinateOne.longitude;
        double latitudeTwo = coordinateTwo.latitude;
        double longitudeTwo= coordinateTwo.longitude;

        double latDistance = Math.toRadians(latitudeTwo - latitudeOne);
        double lonDistance = Math.toRadians(longitudeTwo - longitudeOne);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + Math.cos(Math.toRadians(latitudeOne)) * Math.cos(Math.toRadians(latitudeTwo)) *
                Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distance = R * c;

        return distance;

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        checkLocationPermission();

        // Add a marker in Sydney and move the camera
        LatLng yourloc = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(yourloc).title("Marker on your location!"));
        LatLng myLocation = new LatLng(latitude, longitude);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
                17));

        Toast locationToast = Toast.makeText(getApplicationContext(), String.valueOf(latitude) + String.valueOf(longitude), Toast.LENGTH_LONG);
        locationToast.show();


        ArrayList<LatLng> latlngs = new ArrayList<>();
        MarkerOptions options = new MarkerOptions();
        // gather nearby plant data

        ArrayList<LatLng> DataBase = new ArrayList<>();


        DataBase.add(new LatLng(53.47, -2.2508383));
        DataBase.add(new LatLng(53.47045, -2.204526));
        DataBase.add(new LatLng(53.47045, -2.24052));
        DataBase.add(new LatLng(53.471, -2.2507));

        LatLng userLocationLatLng = new LatLng(latitude, longitude);


        // for all plants in the database
        for (LatLng plants : DataBase) {
            // if they are greater than 2.5 kilometres away then add them to the array list
            // this array list will later be used to populate the map.
            if((HaverSineFormula(userLocationLatLng, plants)) < 5) {
                latlngs.add(plants);
            }
        }

        LatLng plantSpot = new LatLng(55.0, -2.0);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(plantSpot);

        markerOptions.title(plantSpot.latitude + ":" + plantSpot.longitude);

        mMap.addMarker(markerOptions);

        int i = 0;
        for (LatLng plants : latlngs){
            i += 1;
            options.position(plants);
            options.title("Plant" + i);
            options.snippet("Description:" + (LoginActivity.plantDatabase.get(i)));
            mMap.addMarker(options);
        }
    }





    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.



            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        //Request location updates:
                        locationManager.requestLocationUpdates(provider, 400, 1, (LocationListener) this);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }



    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
