package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_drink_setting.*

class DrinkSettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener, View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_setting)
        initListner()

    }

    private fun initListner() {
        simpleSwitch.setOnCheckedChangeListener(this)
        imgBackSpace.setOnClickListener(this)

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

        if (isChecked) {
            Toast.makeText(applicationContext, "ON", Toast.LENGTH_SHORT).show() // display the current state for switch's

            // The toggle is enabled
        } else {
            // The toggle is disabled
            Toast.makeText(applicationContext, "OFF", Toast.LENGTH_SHORT).show() // display the current state for switch's

        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBackSpace -> {
                val intentBack = Intent(this, UserProfileActivity::class.java)
                startActivity(intentBack)
            }
        }

    }
}
