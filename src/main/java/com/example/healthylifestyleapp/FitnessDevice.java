package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FitnessDevice extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBackSpaceFitness;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fitness_device);
        imgBackSpaceFitness=findViewById(R.id.imgBackSpaceFitness);
        imgBackSpaceFitness.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBackSpaceFitness:
                Intent fitnessIntent = new Intent(this, UserProfileActivity.class);
                startActivity(fitnessIntent);
                break;
        }
    }
}
