package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ClickyClickyScreen extends AppCompatActivity implements  View.OnClickListener{

    private TextView onClickText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicky_clicky_screen);

        onClickText = findViewById(R.id.Pressed);

        Button buttonA = findViewById(R.id.button1);
        Button buttonB = findViewById(R.id.button2);
        Button buttonC = findViewById(R.id.button3);
        Button buttonD = findViewById(R.id.button4);
        Button buttonE = findViewById(R.id.button5);
        Button buttonF = findViewById(R.id.button6);

        buttonA.setOnClickListener(this);
        buttonB.setOnClickListener(this);
        buttonC.setOnClickListener(this);
        buttonD.setOnClickListener(this);
        buttonE.setOnClickListener(this);
        buttonF.setOnClickListener(this);

    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1: onClickText.setText(R.string.PressedA);
                break;
            case R.id.button2:  onClickText.setText(R.string.PressedB);
                break;
            case R.id.button3: onClickText.setText(R.string.PressedC);
                break;
            case R.id.button4: onClickText.setText(R.string.PressedD);
                break;
            case R.id.button5: onClickText.setText(R.string.PressedE);
                break;
            case R.id.button6: onClickText.setText(R.string.PressedF);
                break;
        }
    }
}