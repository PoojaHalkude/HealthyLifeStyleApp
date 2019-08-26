package com.example.healthylifestyleapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
     RelativeLayout rlHeaderProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
        initListner();
    }

    private void initListner() {
        rlHeaderProfile.setOnClickListener(this);
    }

    private void initUI() {
        rlHeaderProfile= findViewById(R.id.rlHeaderProfile);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.rlHeaderProfile:
                /*Intent profileIntent= new Intent();
                startActivity(profileIntent);*/

        }
    }
}
