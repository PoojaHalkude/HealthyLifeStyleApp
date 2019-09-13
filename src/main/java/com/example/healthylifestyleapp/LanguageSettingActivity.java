package com.example.healthylifestyleapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LanguageSettingActivity extends AppCompatActivity implements View.OnClickListener {
    /*TextView mTextView;
    Spinner mLanguage;
    ArrayAdapter<String> mAdapter;
*/
    TextView txtEnglish,txtHindi;
    Context context=this;
    private String EngilshCode = "en";
    private String HindiCode = "hi";
ImageView imgBackSpace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
        initUI();
        initListener();




    }

    private void initUI() {
        txtEnglish=findViewById(R.id.txtEnglish);
        txtHindi=findViewById(R.id.txtHindi);
        imgBackSpace=findViewById(R.id.imgBackSpace);
    }

    private void initListener() {
        txtEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateView("en");
            }
        });
       txtHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateView("hi");

             /* //Change Application level locale
                LocaleHelper.setLocale(LanguageSettingActivity.this, HindiCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                Intent intent=new Intent(LanguageSettingActivity.this,MainActivity.class );
                startActivity(intent);*/
            }
        });
        imgBackSpace.setOnClickListener(this);

    }

    private void UpdateView(String langCode) {
        Context context=LocaleHelper.setLocale(this,langCode);
        Resources resources=context.getResources();
        Intent intent=new Intent(LanguageSettingActivity.this,MainActivity.class );
        startActivity(intent);
       // txtEnglish.setText("English");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocaleHelper.setLocale(newBase,"en"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.imgBackSpace:
                Intent intentBack= new Intent(this,SettingsActivity.class);
                startActivity(intentBack);
        }
    }
    // txtEnglish.setOnClickListener(this);

    }


