package com.example.healthylifestyleapp.ui.activities

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_started.*

class StartedActivity : AppCompatActivity(), View.OnClickListener {
    internal var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started)
        initListner()
    }

    private fun initListner() {
        appCompatButtonAge.setOnClickListener(this)
        appCompatButtonHeight.setOnClickListener(this)
        appCompatButtonWeight.setOnClickListener(this)
        appCompatButtonLevel.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.appCompatButtonAge -> {
                val intentAge = Intent(this, ConfirmDetailsActivity::class.java)
                startActivity(intentAge)
            }
            R.id.appCompatButtonHeight -> {
                val intentHeight = Intent(this, ConfirmDetailsActivity::class.java)
                startActivity(intentHeight)
            }
            R.id.appCompatButtonWeight -> {
                val intentWeight = Intent(this, ConfirmDetailsActivity::class.java)
                startActivity(intentWeight)
            }
            R.id.appCompatButtonLevel -> {
                val intentLevel = Intent(this, ConfirmDetailsActivity::class.java)
                startActivity(intentLevel)
            }
        }
    }
}
