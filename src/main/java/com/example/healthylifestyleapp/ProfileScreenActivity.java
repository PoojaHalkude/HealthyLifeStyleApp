package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileScreenActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView ImageViewBackProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_screen);
        ImageViewBackProfile=findViewById(R.id.ImageViewBackProfile);
        ImageViewBackProfile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ImageViewBackProfile:
                Intent intentBack= new Intent(this,UserProfileActivity.class);
                startActivity(intentBack);
                break;
        }
    }
}
