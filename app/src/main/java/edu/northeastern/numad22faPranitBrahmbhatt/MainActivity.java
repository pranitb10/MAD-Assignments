package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMeButton = findViewById(R.id.about_me_button);

        Button clickMeButton = findViewById(R.id.click_me_button);

        Button linkCollectorButton = findViewById(R.id.link_collector_button);

        Button primeTimeButton = findViewById(R.id.prime_time_button);

        Button locationButton = findViewById(R.id.location_main_button);

        aboutMeButton.setOnClickListener(view -> openAboutMeScreen());

        clickMeButton.setOnClickListener(view -> openClickyClickyScreen());

        linkCollectorButton.setOnClickListener(view -> openLinkCollectorScreen());

        primeTimeButton.setOnClickListener(view -> openPrimeTimeScreen());

        locationButton.setOnClickListener(view -> openLocationActivityScreen());

    }

    public void openClickyClickyScreen(){
        Intent openClickyActivity = new Intent(this, ClickyClickyScreen.class);
        startActivity(openClickyActivity);
    }

    public void openAboutMeScreen(){
        Intent openAboutMeActivity = new Intent(this, AboutMeScreen.class);
        startActivity(openAboutMeActivity);
    }

    public void openLinkCollectorScreen(){
        Intent openLinkCollectorActivity = new Intent(this, LinkCollectorScreen.class);
        startActivity(openLinkCollectorActivity);
    }

    public void openPrimeTimeScreen(){
        Intent openPrimeTimeActivity = new Intent(this, PrimeTimeScreen.class);
        startActivity(openPrimeTimeActivity);
    }

    public void openLocationActivityScreen(){
        Intent openlocationActivity = new Intent(this, LocationActivity.class);
        startActivity(openlocationActivity);
    }
}