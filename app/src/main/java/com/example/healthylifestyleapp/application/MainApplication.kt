package com.example.healthylifestyleapp.application

import android.content.Context

import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.utils.LocaleHelper

/* To Do: Default Lanngauge Select en= English*/
class MainApplication : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.onAttach(
                newBase,
                "en"
            )
        )
    }
}
