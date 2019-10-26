package com.example.healthylifestyleapp.ui.customview

import android.app.Activity
import android.app.Dialog
import android.os.Bundle
import android.view.Window

import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.utils.disableWindowClicks
import com.example.healthylifestyleapp.utils.enableWindowClicks

/**
 * A custom Night Picker loader view. This is used on every screen of the application via inheriting by
 * [BaseActivity] class.
 *
 * @property activity

x -= widget.totalPaddingLeft
y -= widget.totalPaddingTop
 */
class UniversalLoader(private val activity: Activity) :
    Dialog(activity, R.style.Loader) {


    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.universal_loader)
        showImageLoader()
    }

    private fun showImageLoader() {
        activity.disableWindowClicks()
    }

    override fun dismiss() {
        super.dismiss()
        activity.enableWindowClicks()
    }
}