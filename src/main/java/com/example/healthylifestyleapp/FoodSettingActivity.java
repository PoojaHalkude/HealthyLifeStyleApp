package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class FoodSettingActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imgBackSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_setting);
        imgBackSpace=findViewById(R.id.imgBackSpace);
        imgBackSpace.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBackSpace:
                Intent intentBack= new Intent(this,UserProfileActivity.class);
                startActivity(intentBack);
        }

    }
}
