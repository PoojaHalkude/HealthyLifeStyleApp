package com.example.healthylifestyleapp;

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class StartedActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton appCompatButtonAge, appCompatButtonHeight, appCompatButtonWeight, appCompatButtonLevel;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started);
        initUI();
        initListner();
    }

    private void initListner() {
        appCompatButtonAge.setOnClickListener(this);
        appCompatButtonHeight.setOnClickListener(this);
        appCompatButtonWeight.setOnClickListener(this);
        appCompatButtonLevel.setOnClickListener(this);

    }

    private void initUI() {
        appCompatButtonAge = findViewById(R.id.appCompatButtonAge);
        appCompatButtonHeight = findViewById(R.id.appCompatButtonHeight);
        appCompatButtonWeight = findViewById(R.id.appCompatButtonWeight);
        appCompatButtonLevel = findViewById(R.id.appCompatButtonLevel);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonAge:
                Intent intentAge = new Intent(this, ConfirmDetailsActivity.class);
                startActivity(intentAge);
                break;
            case R.id.appCompatButtonHeight:
                Intent intentHeight = new Intent(this, ConfirmDetailsActivity.class);
                startActivity(intentHeight);
                break;
            case R.id.appCompatButtonWeight:
                Intent intentWeight = new Intent(this, ConfirmDetailsActivity.class);
                startActivity(intentWeight);
                break;
                case R.id.appCompatButtonLevel:
                Intent intentLevel = new Intent(this, ConfirmDetailsActivity.class);
                startActivity(intentLevel);

                break;
        }
    }
}
