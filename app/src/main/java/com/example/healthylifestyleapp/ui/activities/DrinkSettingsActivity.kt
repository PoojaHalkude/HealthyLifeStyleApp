package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import com.example.healthylifestyleapp.model.Goals
import com.example.healthylifestyleapp.model.Preferences
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_drink_settings.*

class DrinkSettingsActivity : BaseActivity(), CompoundButton.OnCheckedChangeListener {
    private lateinit var preferences: Preferences
    private var goals: Goals? = null
    override fun getRoot(): View? {
        return rootView
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drink_settings)
        initialize()
    }

    private fun initListeners() {
    }

    override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
        updatePreferences(isChecked)
    }

    private fun initialize() {
        setToolbar()
        initListeners()
        fetchPreferences()
        fetchGoals()
    }

    private fun fetchGoals() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (firebaseAuth.currentUser != null) {
            firebaseDatabase.getReference("goals/${firebaseAuth.currentUser!!.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showNoInternetConnectionSnackBar()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        goals = p0.getValue(Goals::class.java)
                        TextViewCountGoal.text = String.format("%dL", goals?.water?.count)
                        fetchProgress()
                    }
                })
        }
    }

    private fun updatePreferences(drinkReminder: Boolean) {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (firebaseAuth.currentUser != null) {
            if (!::preferences.isInitialized) {
                preferences = Preferences(
                    drinkReminder,
                    sleepReminder = false,
                    foodReminder = false
                )
            } else {
                preferences.waterReminder = drinkReminder
            }

            firebaseDatabase.getReference("preferences")
                .child(firebaseAuth.currentUser?.uid!!).setValue(preferences)
        }
    }

    private fun fetchPreferences() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (firebaseAuth.currentUser != null) {
            firebaseDatabase.getReference("preferences/${firebaseAuth.currentUser?.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showNoInternetConnectionSnackBar()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        with(p0.getValue(Preferences::class.java), {
                            if (this != null) preferences = this
                        })
                        if (::preferences.isInitialized) {
                            simpleSwitch.isChecked = preferences.waterReminder
                            simpleSwitch.setOnCheckedChangeListener(this@DrinkSettingsActivity)
                        }
                    }
                })
        }
    }

    private val activities = ArrayList<Activity>()
    private fun fetchProgress() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (firebaseAuth.currentUser != null) {
            firebaseDatabase.getReference("activities/${firebaseAuth.currentUser?.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        showNoInternetConnectionSnackBar()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        activities.clear()
                        p0.children.forEach {
                            val activity = it.getValue(Activity::class.java)
                            activities.add(activity!!)
                        }
                        showDrinkProgress()
                    }
                })
        }
    }

    private fun showDrinkProgress() {
        val drinks = activities?.filter { it.type == "Water" }
        var progress = 0
        drinks?.forEach {
            progress += it.quantity
        }
        val percentage = (100 * progress).div(goals?.water!!.count)
        progressBarDailyGoal.progress = percentage
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.drinks)
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

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}
