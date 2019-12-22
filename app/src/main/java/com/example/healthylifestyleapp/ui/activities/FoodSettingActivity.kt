package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import com.example.healthylifestyleapp.model.Goals
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_food_setting.*

class FoodSettingActivity : BaseActivity() {
    private val activities: ArrayList<Activity>? = ArrayList()
    private var goals: Goals? = null
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_setting)
        initialize()

    }

    private fun initialize() {
        setToolbar()
        fetchGoals()
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.food)
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
                        try {
                            p0.children.iterator().forEach {
                                val activity = it.getValue(Activity::class.java)
                                activities?.add(activity!!)
                            }
                            showFoodProgress()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
        }
    }

    private fun showFoodProgress() {
        val drinks = activities?.filter { it.type == "Food" }
        var progress = 0
        drinks?.forEach {
            progress += it.quantity
        }
        val percentage = (100 * drinks!!.size).div(goals?.food!!)
        progressBarDailyGoal.progress = percentage
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
                        TextViewCountGoal.text = String.format("%d", goals?.food)
                        fetchProgress()
                    }
                })
        }
    }
}
