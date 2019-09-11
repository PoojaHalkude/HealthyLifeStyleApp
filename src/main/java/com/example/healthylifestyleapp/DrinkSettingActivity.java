package com.example.healthylifestyleapp;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DrinkSettingActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
     Switch simpleSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_setting);
        initUI();
        initListner();

    }

    private void initListner() {
        simpleSwitch.setOnCheckedChangeListener(this);

    }

    private void initUI() {
        simpleSwitch = (Switch) findViewById(R.id.simpleSwitch);

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    Toast.makeText(getApplicationContext(), "ON", Toast.LENGTH_SHORT).show(); // display the current state for switch's

                    // The toggle is enabled
                } else {
                    // The toggle is disabled
                    Toast.makeText(getApplicationContext(), "OFF", Toast.LENGTH_SHORT).show(); // display the current state for switch's

                }
    }
}
