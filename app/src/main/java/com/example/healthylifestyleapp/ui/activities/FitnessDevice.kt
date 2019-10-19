package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_fitness_device.*

class FitnessDevice : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fitness_device)
        imgBackSpaceFitness.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBackSpaceFitness -> {
                val fitnessIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(fitnessIntent)
            }
        }
    }
}
