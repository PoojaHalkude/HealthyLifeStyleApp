package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Physique
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_health_data_settings.*
import org.jetbrains.anko.startActivity

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
        firebaseDatabase.getReference("physiques").child("${firebaseAuth.currentUser?.uid}")
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
                String.format("%s", physique.height)
            TextViewWeightCount.text = String.format("%s kg", physique.weight)
            TextViewLevel.text = String.format("%s", physique.level)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.Label_HeathData)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.navigationIcon = ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            R.id.edit -> {
                startActivity<AddVitalsDetailsActivity>()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_health_data, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
