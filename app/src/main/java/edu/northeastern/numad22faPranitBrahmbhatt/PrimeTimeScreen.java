package edu.northeastern.numad22faPranitBrahmbhatt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeTimeScreen extends AppCompatActivity {

    int i, j, prime = 3, lastNum, count = 0;
    boolean endThread = false, check = true;
    TextView onClickChangeLastPrimeText;
    TextView onClickChangeLastNumberText;
    Thread findPrimeThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_time);

        Button findPrimesButton = findViewById(R.id.find_prime_button);
        Button terminateSearchButton = findViewById(R.id.terminate_search_button);

        onClickChangeLastPrimeText = findViewById(R.id.PrimeNumberDisplayed);
        onClickChangeLastNumberText = findViewById(R.id.LastNumberDisplayed);

        findPrimeThread = new Thread(
                new Runnable() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void run() {
                        while (!endThread) {
                            for (i = 3; i <= 10000; i = i + 2) {
                                for (j = 2; j <= i / 2; j++) {
                                    if (i % j == 0) {
                                        check = false;
                                        break;
                                    }
                                    lastNum = i;
                                    check = true;
                                }
                                if (check) {
                                    prime = i;
                                }
                            }
                        }
                    }
                }
        );

        findPrimesButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        findPrimeThread.start();
                        System.out.println("Thread Started");
                    }
                }
        );

        terminateSearchButton.setOnClickListener(
                new View.OnClickListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onClick(View view) {
                        endThread = true;
                        System.out.println("Thread Terminated");
                        System.out.println(prime);
                        System.out.println(lastNum);
                        onClickChangeLastPrimeText.setText(
                                Integer.toString(prime)
                        );
                        onClickChangeLastNumberText.setText(
                                Integer.toString(lastNum)
                        );
                    }
                }
        );
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertdialog = new AlertDialog.Builder(PrimeTimeScreen.this);
        alertdialog.setTitle("Alert!");
        alertdialog.setMessage("Are you sure you want to terminate the Prime search?");
        alertdialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                findPrimeThread.interrupt();
            }
        });

        alertdialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert=alertdialog.create();
        alertdialog.show();
    }
}