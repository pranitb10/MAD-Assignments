package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class LocationActivity extends AppCompatActivity {

    TextView latValue, longValue;
    Button getLocation;
    FusedLocationProviderClient locationProviderClient;
    LocationManager locManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latValue = findViewById(R.id.LatitudeValue);
        longValue = findViewById(R.id.LongitudeValue);
        getLocation = findViewById(R.id.getLocationButton);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getLocation.setOnClickListener(view -> openLocationPermission());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 10) {
            if(grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openLocationPermission();
            }
        } else {
            Toast.makeText(LocationActivity.this,
                    "Location Permission is Required.", Toast.LENGTH_SHORT).show();
        }
    }

    public void openLocationPermission() {
        if(locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationProviderClient.getLastLocation().addOnSuccessListener(this,
                        new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location userLocation) {
                                if (userLocation != null) {
                                    updateLocation(userLocation);
                                }
                            }
                        });
            } else {
                requestUserPermission();
            }
        } else {
            showAlertMessageLocationDisabled();
        }
    }

    private void showAlertMessageLocationDisabled() {
        AlertDialog.Builder locationEnablerBuilder = new AlertDialog.Builder(this);
        locationEnablerBuilder.setMessage("You are asked to turn on the location of your device."
                + "Do you want to continue enabling the location of your device?");
        locationEnablerBuilder.setCancelable(false);
        locationEnablerBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        locationEnablerBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog alertDialog = locationEnablerBuilder.create();
        alertDialog.show();
    }

    public void requestUserPermission(){
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
    }

    public void updateLocation(Location location) {
       latValue.setText(String.valueOf(location.getLatitude()));
       longValue.setText(String.valueOf(location.getLongitude()));
    }
}