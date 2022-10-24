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
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Timed;

public class LocationActivity extends AppCompatActivity {

    TextView latValue, longValue, distanceTv;
    boolean locationPermission = false;
    Location oldLoc, newLoc;
    double distance = 0;
    Button getLocation, resetButton;
    FusedLocationProviderClient locationProviderClient;
    LocationManager locManager;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latValue = findViewById(R.id.LatitudeValue);
        longValue = findViewById(R.id.LongitudeValue);
        distanceTv = findViewById(R.id.DistanceValue);
        getLocation = findViewById(R.id.GetLocation);
        resetButton = findViewById(R.id.resetDistanceButton);

        locationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        getLocation.setOnClickListener(view -> {
            locationPermission = true;
            openLocationPermission();
        });

        resetButton.setOnClickListener(view -> {
            compositeDisposable.dispose();
            distanceTv.setText(String.valueOf(0.0));
        });

        if(locationPermission) {
            Observable.interval(10L, TimeUnit.SECONDS)
                    .timeInterval().subscribeWith(
                            new Observer<Timed<Long>>() {
                                @Override
                                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull
                                                                Disposable d) {
                                    System.out.println("subscribe");
                                    compositeDisposable.add(d);
                                }

                                @Override
                                public void onNext(@io.reactivex.rxjava3.annotations.NonNull Timed<Long>
                                                           longTimed) {
                                    System.out.println("next");
                                    openLocationPermission();
                                }

                                @Override
                                public void onError(Throwable t) {
                                    System.out.println("onError -> " + t.getMessage());
                                }

                                @Override
                                public void onComplete() {
                                    System.out.println("complete");
                                }
                            }
                    );
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        openLocationPermission();
                    }
                }
            }
        };
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
        LocationServices.getFusedLocationProviderClient(getApplicationContext()).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
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
                    Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED) {
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

    public void requestUserPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 10);
    }

    public void updateLocation(Location location) {
        newLoc = location;
       latValue.setText(String.valueOf(location.getLatitude()));
       longValue.setText(String.valueOf(location.getLongitude()));
        if(oldLoc == null){
            oldLoc = newLoc;
            distance += calculateDistance(oldLoc.getLatitude(), newLoc.getLatitude(),
                    oldLoc.getLongitude(), newLoc.getLongitude());
        } else if (newLoc.getLatitude() != oldLoc.getLatitude() && newLoc.getLongitude()
               != oldLoc.getLongitude()){
           distance += calculateDistance(oldLoc.getLatitude(), newLoc.getLatitude(),
                   oldLoc.getLongitude(), newLoc.getLongitude());
           oldLoc = newLoc;
       }
        distanceTv.setText(String.valueOf(distance));
    }

    public static double calculateDistance(double lat1, double lat2, double lon1,
                                  double lon2) {
        final double R = 6378100; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    @Override
    public void onBackPressed() {
        if (true) {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(LocationActivity.this);
            alertdialog.setTitle("Alert!");
            alertdialog.setMessage("Are you sure you want to dismiss the Distance Tracker?");
            alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

            AlertDialog alert = alertdialog.create();
            alertdialog.show();
        } else {
            finish();
        }
    }

}