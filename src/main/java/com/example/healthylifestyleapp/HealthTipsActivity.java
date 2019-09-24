package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HealthTipsActivity extends AppCompatActivity implements View.OnClickListener {
ImageView imgBackSpaceHealthTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_tips);
        imgBackSpaceHealthTips=findViewById(R.id.imgBackSpaceHealthTips);
        imgBackSpaceHealthTips.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBackSpaceHealthTips:
                Intent fitnessIntent = new Intent(this, UserProfileActivity.class);
                startActivity(fitnessIntent);
                break;
        }
    }
}
