package com.example.healthylifestyleapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class LanguageSettingActivity extends AppCompatActivity implements View.OnClickListener {

    /*Spinner mLanguage;
    ArrayAdapter<String> mAdapter;*/

    TextView txtEnglish,txtHindi;
    Context context=this;
    private static final String EngilshCode = "en";
    private static final String HindiCode = "hi";
    ImageView imgBackSpace;
    private static final String LOCALE_KEY = "localekey";
    private static Locale myLocale;
    private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    //Shared Preferences Variables
   /* private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_setting);
        initUI();
        initListener();
        loadLocale();



    }

    private void initListener() {
        txtEnglish.setOnClickListener(this);
        txtHindi.setOnClickListener(this);
        imgBackSpace.setOnClickListener(this);
    }

    public void loadLocale() {
        String language = sharedPreferences.getString(Locale_KeyValue, "");
        changeLocale(language);
    }
    @Override
    public void onClick(View view) {
        String lang = "en";//Default Language
        switch (view.getId()) {
            case R.id.txtEnglish:
                lang = "en";
                Intent intent1=new Intent(LanguageSettingActivity.this,UserProfileActivity.class );
                startActivity(intent1);
                break;
            case R.id.txtHindi:
                lang = "hi";
                Intent intent2=new Intent(LanguageSettingActivity.this,UserProfileActivity.class );
                startActivity(intent2);
                break;
            case R.id.imgBackSpace:
                Intent intent=new Intent(LanguageSettingActivity.this,SettingsActivity.class );
                startActivity(intent);
                break;
        }
        changeLocale(lang);//Change Locale on selection basis

    }

    private void initUI() {
        txtEnglish=findViewById(R.id.txtEnglish);
        txtHindi=findViewById(R.id.txtHindi);
        imgBackSpace=findViewById(R.id.imgBackSpace);
        sharedPreferences = getSharedPreferences(Locale_Preference, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    private void changeLocale(String lang) {

        if (lang.equalsIgnoreCase(""))
            return;
        myLocale = new Locale(lang);//Set Selected Locale
        saveLocale(lang);//Save the selected locale
        Locale.setDefault(myLocale);//set new locale as default
        Configuration config = new Configuration();//get Configuration
        config.locale = myLocale;//set config locale as selected locale
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());//Update the config
       // updateTexts();//Update texts according to locale

    }

    private void saveLocale(String lang) {
        editor.putString(Locale_KeyValue, lang);
        editor.commit();
    }

    /*private void initListener() {
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

             *//* //Change Application level locale
                LocaleHelper.setLocale(LanguageSettingActivity.this, HindiCode);

                //It is required to recreate the activity to reflect the change in UI.
                recreate();
                Intent intent=new Intent(LanguageSettingActivity.this,MainActivity.class );
                startActivity(intent);*//*
            }
        });
        imgBackSpace.setOnClickListener(this);

    }*/

   /* private void UpdateView(String langCode) {
        Context context=LocaleHelper.setLocale(this,langCode);
        Resources resources=context.getResources();
        Intent intent=new Intent(LanguageSettingActivity.this,MainActivity.class );
        startActivity(intent);
//        resources.getString(Integer.parseInt(langCode));
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
    }*/
    // txtEnglish.setOnClickListener(this);

    }


