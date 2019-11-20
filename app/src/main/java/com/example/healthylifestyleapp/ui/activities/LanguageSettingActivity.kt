package com.example.healthylifestyleapp.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_language_setting.*
import java.util.*

class LanguageSettingActivity : AppCompatActivity(), View.OnClickListener {

    /*Spinner mLanguage;
    ArrayAdapter<String> mAdapter;*/


    internal var context: Context = this
    //Shared Preferences Variables
    /* private static final String Locale_Preference = "Locale Preference";
    private static final String Locale_KeyValue = "Saved Locale";
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_setting)
        initUI()
        initListener()
        loadLocale()


    }

    private fun initListener() {
        txtEnglish.setOnClickListener(this)
        txtHindi.setOnClickListener(this)
        imgBackSpace.setOnClickListener(this)
    }

    fun loadLocale() {
        val language = sharedPreferences!!.getString(
            Locale_KeyValue, "")
        changeLocale(language!!)
    }

    override fun onClick(view: View) {
        var lang = "en"//Default Language
        when (view.id) {
            R.id.txtEnglish -> {
                lang = "en"
                val intent1 = Intent(this@LanguageSettingActivity, UserProfileActivity::class.java)
                startActivity(intent1)
            }
            R.id.txtHindi -> {
                lang = "hi"
                val intent2 = Intent(this@LanguageSettingActivity, UserProfileActivity::class.java)
                startActivity(intent2)
            }
            R.id.imgBackSpace -> {

            }
        }
        changeLocale(lang)//Change Locale on selection basis

    }

    private fun initUI() {
        sharedPreferences = getSharedPreferences(
            Locale_Preference, Activity.MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
    }

    private fun changeLocale(lang: String) {

        if (lang.equals("", ignoreCase = true))
            return
        myLocale = Locale(lang)//Set Selected Locale
        saveLocale(lang)//Save the selected locale
        Locale.setDefault(myLocale)//set new locale as default
        val config = Configuration()//get Configuration
        config.locale =
            myLocale//set config locale as selected locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)//Update the config
        // updateTexts();//Update texts according to locale

    }

    private fun saveLocale(lang: String) {
        editor!!.putString(
            Locale_KeyValue, lang)
        editor!!.commit()
    }

    companion object {
        private val EngilshCode = "en"
        private val HindiCode = "hi"
        private val LOCALE_KEY = "localekey"
        private var myLocale: Locale? = null
        private val Locale_Preference = "Locale Preference"
        private val Locale_KeyValue = "Saved Locale"
        private var sharedPreferences: SharedPreferences? = null
        private var editor: SharedPreferences.Editor? = null
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
                Intent intent=new Intent(LanguageSettingActivity.this,OnBoardingOptionsActivity.class );
                startActivity(intent);*//*
            }
        });
        imgBackSpace.setOnClickListener(this);

    }*/

    /* private void UpdateView(String langCode) {
        Context context=LocaleHelper.setLocale(this,langCode);
        Resources resources=context.getResources();
        Intent intent=new Intent(LanguageSettingActivity.this,OnBoardingOptionsActivity.class );
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


