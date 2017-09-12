package com.example.avnijain.playservices;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationProviderClient;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    boolean mRequestingLocationUpdates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        Log.i("Avni","after Setting req");
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();
                if (location != null) {
                    Toast.makeText(MainActivity.this,
                            "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
                            , Toast.LENGTH_LONG).show();
                    Intent i = new Intent(MainActivity.this, MapsActivity.class);
                    i.putExtra("LAT",location.getLatitude());
                    i.putExtra("LONG",location.getLongitude());
                    startActivity(i);


                }
            }
        };
        Log.i("Avni","after Setting callback");


    }

    public void getLocation(View view) {

        Toast.makeText(this, "Button Click", Toast.LENGTH_SHORT).show();

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return;
//        }
//        mFusedLocationProviderClient.getLastLocation()
//                .addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    Toast.makeText(MainActivity.this,
//                            "Lat: " + location.getLatitude() + " Long: " + location.getLongitude()
//                            , Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        startRequestingUpdates();

    }


    void startRequestingUpdates() {

        Log.i("Avni","startReq");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationProviderClient
                .requestLocationUpdates(mLocationRequest,
                        mLocationCallback, null);

        mRequestingLocationUpdates = true;



    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRequestingLocationUpdates){
            mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
            mRequestingLocationUpdates = false;
        }
    }
}

