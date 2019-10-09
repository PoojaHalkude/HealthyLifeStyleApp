package com.example.healthylifestyleapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {
     RelativeLayout rlHeaderProfile,rlHeaderLogOut,rlHeaderLanguage,rlHeaderHealthData,rlHeaderReferFriend,rlHeaderFeedback;
     ImageView ImageViewHome,ImageViewActivity,ImageViewSettings,ImageViewMenuSetting;
     Context context=this;
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
        ImageViewMenuSetting.setOnClickListener(this);
        rlHeaderHealthData.setOnClickListener(this);
        rlHeaderLanguage.setOnClickListener(this);
        rlHeaderReferFriend.setOnClickListener(this);
        rlHeaderFeedback.setOnClickListener(this);
    }

    private void initUI() {
        rlHeaderProfile= findViewById(R.id.rlHeaderProfile);
        ImageViewSettings=findViewById(R.id.ImageViewSettings);
        ImageViewHome=findViewById(R.id.ImageViewHome);
        ImageViewActivity=findViewById(R.id.ImageViewActivity);
        rlHeaderLogOut=findViewById(R.id.rlHeaderLogOut);
        ImageViewMenuSetting=findViewById(R.id.ImageViewMenuSetting);
        rlHeaderLanguage=findViewById(R.id.rlHeaderLanguage);
        rlHeaderHealthData=findViewById(R.id.rlHeaderHealthData);
        rlHeaderReferFriend=findViewById(R.id.rlHeaderReferFriend);
        rlHeaderFeedback=findViewById(R.id.rlHeaderFeedback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlHeaderLogOut:
                // custom dialog
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.custom_daiolg_logout);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                //TextView text = (TextView) dialog.findViewById(R.id.text);
                //text.setText("Android custom dialog example!");
                Button ButtonNo=(Button) dialog.findViewById(R.id.ButtonNo);
                Button ButtonYes= (Button) dialog.findViewById(R.id.ButtonYes);
                // if button is clicked, close the custom dialog
                ButtonNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent logoutIntent = new Intent(SettingsActivity.this, MainActivity.class);

                        dialog.dismiss();
                    }
                });
                ButtonYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent logoutIntent = new Intent(SettingsActivity.this, MainActivity.class);
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(logoutIntent);
                    }
                });
                dialog.show();

                break;
                case R.id.ImageViewSettings:
                Intent settingIntent = new Intent(this, UserProfileActivity.class);
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

            case R.id.rlHeaderLanguage:
                Intent langIntent= new Intent (this,LanguageSettingActivity.class);
                startActivity(langIntent);
                break;

            case R.id.rlHeaderHealthData:
                Intent HealthDataIntent= new Intent (this,HealthDataSettingsActivity.class);
                startActivity(HealthDataIntent);
                break;
            case R.id.rlHeaderProfile:
                Intent ProfileIntent= new Intent (this,ProfileScreenActivity.class);
                startActivity(ProfileIntent);
                break;
            case R.id.rlHeaderReferFriend:
               /* Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);*/
                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "This is my text to send.";
                String shareSub = "Your subject/Link";
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(myIntent, "Share Link using"));
                break;
            case R.id.ImageViewMenuSetting:
                Intent MenuIntent= new Intent (this,UserProfileActivity.class);
                startActivity(MenuIntent);
                break;
            case R.id.rlHeaderFeedback:
                Intent feedbackIntent= new Intent(this, FeedbackFormActivity.class);
                startActivity(feedbackIntent);
        }

    }
}
