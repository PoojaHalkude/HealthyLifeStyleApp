package com.example.healthylifestyleapp.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import com.example.healthylifestyleapp.model.Goals
import com.example.healthylifestyleapp.ui.activities.DrinkSettingsActivity
import com.example.healthylifestyleapp.ui.activities.FoodSettingActivity
import com.example.healthylifestyleapp.ui.activities.HealthTipsActivity
import com.example.healthylifestyleapp.ui.activities.SleepSettingActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), View.OnClickListener {
    private val activities: ArrayList<Activity>? = ArrayList()
    private var goals: Goals? = null

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.LLHeaderDrink -> {
                startActivity<DrinkSettingsActivity>()

            }
            R.id.LLHeaderSleep -> {
                startActivity<SleepSettingActivity>()
            }
            R.id.LLHeaderFood -> {
                startActivity<FoodSettingActivity>()
            }

            R.id.llHeaderTips -> {
                startActivity<HealthTipsActivity>()
            }
        }
    }

    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LLHeaderDrink.setOnClickListener(this)
        LLHeaderSleep.setOnClickListener(this)
        LLHeaderFood.setOnClickListener(this)
        llHeaderTips.setOnClickListener(this)
        setSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        fetchGoals()
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchGoals()
        }
    }

    private fun fetchProgress() {
        if (!isVisible) {
            return
        }
        if (!isNetworkAccessible(context!!)) {
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
                            showProgress()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
        }
    }

    private fun showProgress() {
        showDrinkProgress()
        showFoodProgress()
        showSleepProgress()
        swipeRefreshLayout.isRefreshing = false
    }

    private fun showSleepProgress() {
        val drinks = activities?.filter { it.type == "Sleep" }
        var progress = 0
        drinks?.forEach {
            progress += it.quantity
        }
        val percentage = (100 * progress).div(goals?.sleep!!)
        progressBarSleep.progress = percentage.toFloat()
    }

    private fun showFoodProgress() {
        val drinks = activities?.filter { it.type == "Food" }
        var progress = 0
        drinks?.forEach {
            progress += it.quantity
        }
        val percentage = (100 * drinks!!.size).div(goals?.food!!)
        progressBarFood.progress = percentage.toFloat()
    }

    private fun showDrinkProgress() {
        val drinks = activities?.filter { it.type == "Water" }
        var progress = 0
        drinks?.forEach {
            progress += it.quantity
        }
        val percentage = (100 * progress).div(goals?.water!!.count)
        progressBarDrink.progress = percentage.toFloat()
    }

    private fun fetchGoals() {
        resetProgress()
        if (!isNetworkAccessible(context!!)) {
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
                        fetchProgress()
                    }
                })
        }
    }

    private fun resetProgress() {
        activities?.clear()
        goals = null
        progressBarDrink.progress = 0f
        progressBarFood.progress = 0f
        progressBarSleep.progress = 0f
    }


}
