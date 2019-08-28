package com.example.healthylifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

public class HomeScreenActivity extends AppCompatActivity {
    ProgressBar progressBarDrink,progressBarSleep,progressBarFood;
    private int pStatus = 0;
    private Handler handler = new Handler();
    private TextView textViewProgressDrink,textViewProgressFood,textViewProgressSleep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        progressBarDrink = (ProgressBar) findViewById(R.id.progressBarDrink);
        progressBarFood=findViewById(R.id.progressBarFood);
        progressBarSleep=findViewById(R.id.progressBarSleep);

        textViewProgressDrink=findViewById(R.id.textViewProgressDrink);
        textViewProgressFood=findViewById(R.id.textViewProgressFood);
        textViewProgressSleep=findViewById(R.id.textViewProgressSleep);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 30) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarDrink.setProgress(pStatus);
                            textViewProgressDrink.setText(pStatus + " ltr");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
                while (pStatus <= 40) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarFood.setProgress(pStatus);
                            textViewProgressFood.setText(pStatus + "" +
                                    "" +
                                    "g");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;

                }
                while (pStatus <= 9.40) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBarSleep.setProgress(pStatus);
                            textViewProgressSleep.setText(pStatus + " ltr");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
            }

        }).start();

    }
    }

