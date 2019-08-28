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

public class PhysiquesDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton AppCompatButtonFinish;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physiques_details);
        AppCompatButtonFinish=findViewById(R.id.AppCompatButtonFinish);
        AppCompatButtonFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AppCompatButtonFinish:
                //Luanching the new activity
                Intent newIntent= new Intent(this, ConfirmDetailsActivity.class);
                startActivity(newIntent);
        }
    }
}
