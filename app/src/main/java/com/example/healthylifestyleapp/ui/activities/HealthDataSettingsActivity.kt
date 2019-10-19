package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_health_data_settings.*

class HealthDataSettingsActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_data_settings)
        imgBackSpace.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBackSpace -> {
                val intentBack = Intent(this, SettingsActivity::class.java)
                startActivity(intentBack)
            }
        }
    }
}
