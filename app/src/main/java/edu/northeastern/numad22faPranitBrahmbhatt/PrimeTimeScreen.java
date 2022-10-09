package edu.northeastern.numad22faPranitBrahmbhatt;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class PrimeTimeScreen extends AppCompatActivity {

    long i, j, prime = 3, lastNum;
    boolean endThread = false, check = true;
    TextView onClickChangeLastPrimeText;
    TextView onClickChangeLastNumberText;
    Thread findPrimeThread = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_time);

        Button findPrimesButton = findViewById(R.id.find_prime_button);
        Button terminateSearchButton = findViewById(R.id.terminate_search_button);

        onClickChangeLastPrimeText = findViewById(R.id.PrimeNumberDisplayed);
        onClickChangeLastNumberText = findViewById(R.id.LastNumberDisplayed);

        findPrimesButton.setOnClickListener(
                view -> {
                    findPrimeFunction();
                    System.out.println("Thread Started");
                }
        );

        terminateSearchButton.setOnClickListener(
                view -> {
                    endThread = true;
//                    if (findPrimeThread != null) {
//                        findPrimeThread.interrupt();
//                        findPrimeThread = null;
//                    }
                    onClickChangeLastPrimeText.setText(
                            Long.toString(prime)
                    );
                    onClickChangeLastNumberText.setText(
                            Long.toString(lastNum)
                    );
                    System.out.println("Thread Terminated");
                }
        );
    }

    @Override
    public void onBackPressed() {
        if (endThread) {
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

    public void findPrimeFunction(){
            findPrimeThread = new Thread(
                    () -> {
                            for (i = 3; ; i = i + 2) {
                                if(endThread) {
                                    return;
                                }
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
                                runOnUiThread(new Runnable() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void run() {
                                        onClickChangeLastPrimeText.setText(
                                                Long.toString(prime)
                                        );
                                        onClickChangeLastNumberText.setText(
                                                Long.toString(lastNum)
                                        );
                                    }
                                });
                            }
                    }
            );
        findPrimeThread.start();
    }
}