package com.example.healthylifestyleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.ui.adapters.ActivitiesListAdapter
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_daily_agenda_activity.*

class ActivitiesFragment : BaseFragment() {
    private val activities: ArrayList<Activity> = ArrayList()

    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_daily_agenda_activity, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
    }

    private fun initialize() {
        fetchProgress()
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
                                activities.add(activity!!)
                            }
                            loadData()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
        }
    }

    private fun loadData() {
        if (!isVisible) {
            return
        }
        activities.reverse()
        val adapter = ActivitiesListAdapter(activities)
        rvActivities.layoutManager = LinearLayoutManager(context)
        rvActivities.adapter = adapter
    }
}