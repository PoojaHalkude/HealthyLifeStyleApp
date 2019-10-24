package com.example.healthylifestyleapp.application

import android.app.Application
import android.content.Context
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.BuildConfig
import com.example.healthylifestyleapp.utils.LocaleHelper
import com.google.firebase.FirebaseApp
import io.fabric.sdk.android.Fabric

/* To Do: Default Language Select en= English*/
class MainApplication : Application() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(
            LocaleHelper.onAttach(
                newBase,
                "en"
            )
        )
    }

    override fun onCreate() {
        super.onCreate()
        initializeFirebase()
        initializeCrashlytics()
    }

    private fun initializeFirebase() {
        FirebaseApp.initializeApp(this)
    }

    /**
     * Initialize [Crashlytics] to log the crashes in the application.
     * The library is also used to report exceptions that are non-fatal.
     *
     */
    private fun initializeCrashlytics() = Fabric.with(
        Fabric.Builder(this)
            .kits(Crashlytics())
            .debuggable(BuildConfig.DEBUG)
            .build()
    )!!
}
