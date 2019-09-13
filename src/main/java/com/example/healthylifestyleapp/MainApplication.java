package com.example.healthylifestyleapp;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

/* To Do: Default Lanngauge Select en= English*/
public class MainApplication extends AppCompatActivity {
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase,"en"));
    }
}
