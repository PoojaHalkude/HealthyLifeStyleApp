package com.example.healthylifestyleapp.utils

import android.app.Activity
import android.net.Uri
import android.view.WindowManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.example.healthylifestyleapp.R

fun Activity.disableWindowClicks() {
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun Activity.enableWindowClicks() {
    window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
}

fun Activity.loadChromeCustomTabFromUrl(url: String) {
    val builder: CustomTabsIntent.Builder = CustomTabsIntent.Builder()
    builder.setToolbarColor(ContextCompat.getColor(this, R.color.colorAccent))
    builder.setSecondaryToolbarColor(ContextCompat.getColor(this, android.R.color.white))
    builder.setShowTitle(true)
    builder.enableUrlBarHiding()
    val customTabsIntent: CustomTabsIntent = builder.build()
    customTabsIntent.launchUrl(this, Uri.parse(url))
}