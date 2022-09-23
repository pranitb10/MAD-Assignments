package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutMeButton = (Button)findViewById(R.id.about_me_button);

        Button clickMeButton = (Button)findViewById(R.id.click_me_button);

        aboutMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText( getApplicationContext(), R.string.my_info, Toast.LENGTH_LONG).show();
            }
        });

        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClickyClickyScreen();
            }
        });
    }

    public void openClickyClickyScreen(){
        Intent openClickyActivity = new Intent(this, ClickyClickyScreen.class);
        startActivity(openClickyActivity);
    }
}