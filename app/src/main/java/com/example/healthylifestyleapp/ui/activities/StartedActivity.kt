package com.example.healthylifestyleapp.ui.activities

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_started.*

class StartedActivity : BaseActivity(), View.OnClickListener {
    override fun getRoot(): View {
        return rootView
    }

    internal var context: Context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_started)
        initListener()
    }

    private fun initListener() {
        appCompatButtonAge.setOnClickListener(this)
        appCompatButtonHeight.setOnClickListener(this)
        appCompatButtonWeight.setOnClickListener(this)
        appCompatButtonLevel.setOnClickListener(this)
        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
        }
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
