package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.fragments.FragmentEmail
import com.example.healthylifestyleapp.ui.fragments.FragmentPhone
import kotlinx.android.synthetic.main.activity_manual_login.*

class ManualLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_login)
        initialize()
        readIntent()
    }

    private fun initialize() {
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun readIntent() {
        val method = intent.getStringExtra("method")
        if (method == "email") {
            inflateFragment(FragmentEmail())
            supportActionBar?.title = "Login with email address"
        } else {
            inflateFragment(FragmentPhone())
            supportActionBar?.title = "Login with mobile number"
        }
    }

    private fun inflateFragment(fr: Fragment) {
        supportFragmentManager.beginTransaction().replace(container.id, fr, fr.tag).commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}
