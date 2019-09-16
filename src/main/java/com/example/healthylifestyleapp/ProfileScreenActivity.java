package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ImageViewBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        ImageViewBack.findViewById(R.id.ImageViewBack);
        ImageViewBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ImageViewBack:
                Intent intentBack= new Intent(this,UserProfileActivity.class);
                startActivity(intentBack);
                break;
        }
    }
}
