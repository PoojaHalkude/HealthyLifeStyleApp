package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_goal_settings.*

class ActivityGoalSettings : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_settings)
        imgBackSpace.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {

        }

    }
}
