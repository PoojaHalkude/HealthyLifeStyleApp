package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_drink_setting.*

class DrinkSettingActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_setting)
        initialize()
    }

    private fun initListner() {
        simpleSwitch.setOnCheckedChangeListener(this)

    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {

        if (isChecked) {
            Toast.makeText(applicationContext, "ON", Toast.LENGTH_SHORT)
                .show() // display the current state for switch's

            // The toggle is enabled
        } else {
            // The toggle is disabled
            Toast.makeText(applicationContext, "OFF", Toast.LENGTH_SHORT)
                .show() // display the current state for switch's

        }
    }

    private fun initialize() {
        setToolbar()
        initListner()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.drinks)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onClick(v: View) {
        when (v.id) {
            android.R.id.home -> {
                onBackPressed()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
