package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_profile_screen.*

class ProfileScreenActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_screen)
        rlHeaderFitnessDevice.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlHeaderFitnessDevice -> {
                val fitnessIntent = Intent(this, FitnessDevice::class.java)
                startActivity(fitnessIntent)
            }
        }
    }
}
