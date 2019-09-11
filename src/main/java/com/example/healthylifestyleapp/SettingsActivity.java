package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
     RelativeLayout rlHeaderProfile,rlHeaderLogOut;
     ImageView ImageViewHome,ImageViewActivity,ImageViewSettings,ImageViewMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initUI();
        initListner();
    }

    private void initListner() {
        rlHeaderProfile.setOnClickListener(this);
        ImageViewSettings.setOnClickListener(this);
        ImageViewHome.setOnClickListener(this);
        ImageViewActivity.setOnClickListener(this);
        rlHeaderLogOut.setOnClickListener(this);
        ImageViewMenu.setOnClickListener(this);

    }

    private void initUI() {
        rlHeaderProfile= findViewById(R.id.rlHeaderProfile);
        ImageViewSettings=findViewById(R.id.ImageViewSettings);
        ImageViewHome=findViewById(R.id.ImageViewHome);
        ImageViewActivity=findViewById(R.id.ImageViewActivity);
        rlHeaderLogOut=findViewById(R.id.rlHeaderLogOut);
        ImageViewMenu=findViewById(R.id.ImageViewMenu);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlHeaderLogOut:
                Intent logoutIntent = new Intent(this, MainActivity.class);
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logoutIntent);
                break;
                case R.id.ImageViewSettings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.ImageViewHome:
                Intent HomeIntent = new Intent(this, UserProfileActivity.class);
                startActivity(HomeIntent);
                break;
            case R.id.ImageViewActivity:
                Intent ActivityIntent = new Intent(this, DailyAgendaACtivity.class);
                startActivity(ActivityIntent);
                break;

           /* case R.id.ImageViewMenu:
                Intent MenuIntent= new Intent (this,)*/

        }
    }
}
