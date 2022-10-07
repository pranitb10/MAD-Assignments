package edu.northeastern.numad22faPranitBrahmbhatt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class PrimeTimeScreen extends AppCompatActivity {

    int i, j, prime = 3, lastNum, count = 0;
    boolean endThread = false, check = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prime_time);

        Button findPrimesButton = findViewById(R.id.find_prime_button);
        Button terminateSearchButton = findViewById(R.id.terminate_search_button);

        Thread findPrimeThread = new Thread(
                new Runnable() {

                    @Override
                    public void run() {
                        while (!endThread) {
                            for(i = 3; i <= 20; i = i + 2) {
                                for(j = 2; j <= i / 2; j++){
                                    if(i % j == 0){
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
                    @Override
                    public void onClick(View view) {
                        endThread = true;
                        System.out.println("Thread Terminated");
                        System.out.println(prime);
                        System.out.println(lastNum);
                    }
                }
        );
    }
}