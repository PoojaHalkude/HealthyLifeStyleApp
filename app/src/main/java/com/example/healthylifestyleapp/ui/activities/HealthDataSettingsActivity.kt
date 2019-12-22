package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Physique
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_health_data_settings.*

class HealthDataSettingsActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_health_data_settings)
        initialize()
    }

    override fun onStart() {
        super.onStart()

    }

    private fun initialize() {
        setToolbar()
        fetchHealthData()
    }

    private fun fetchHealthData() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        showProgressDialog()
        firebaseDatabase.getReference("preferences").child("${firebaseAuth.currentUser?.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    dismissProgressDialog()
                    val physique = p0.getValue(Physique::class.java)
                    showHealthData(physique)
                }
            })
    }

    private fun showHealthData(physique: Physique?) {
        if (physique != null) {
            TextViewAgeCount.text = String.format("%s", physique.age)
            TextViewHeightCount.text =
                String.format("%s %s", physique.height, if (physique.isHeightInCm) "cm" else "ft")
            TextViewWeightCount.text = String.format("%s kg", physique.weight)
            TextViewLevel.text = String.format("%s", physique.level)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.Label_HeathData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
