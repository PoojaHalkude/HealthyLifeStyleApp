package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_health_tips.*

class HealthTipsActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_tips)
        imgBackSpaceHealthTips.setOnClickListener(this)
        setWebView()
    }

    private fun setWebView() {
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://www.fitnessblender.com/")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBackSpaceHealthTips -> {
                val fitnessIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(fitnessIntent)
            }
        }
    }
}
