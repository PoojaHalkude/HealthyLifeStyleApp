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
import kotlinx.android.synthetic.main.activity_physiques_details.*

class PhysiquesDetailsActivity : AppCompatActivity(), View.OnClickListener {
    internal var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_physiques_details)
        AppCompatButtonFinish.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.AppCompatButtonFinish -> {
                //Luanching the new activity
                val newIntent = Intent(this, ConfirmDetailsActivity::class.java)
                startActivity(newIntent)
            }
        }
    }
}
