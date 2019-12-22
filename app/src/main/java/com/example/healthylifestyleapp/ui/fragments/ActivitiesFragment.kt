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
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompat.v7.Appcompat
import org.jetbrains.anko.noButton
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.yesButton

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
        setSwipeRefreshLayout()
    }

    override fun onResume() {
        super.onResume()
        fetchProgress()
    }

    private fun setSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            fetchProgress()
        }
    }

    private fun fetchProgress() {
        activities.clear()
        if (!isVisible) {
            return
        }
        if (!isNetworkAccessible(context!!)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (firebaseAuth.currentUser != null) {
            showProgressDialog()
            firebaseDatabase.getReference("activities/${firebaseAuth.currentUser?.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        dismissProgressDialog()
                        showNoInternetConnectionSnackBar()
                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        try {
                            dismissProgressDialog()
                            swipeRefreshLayout?.isRefreshing = false
                            p0.children.iterator().forEach {
                                val activity = it.getValue(Activity::class.java)
                                activity?.id = it.key!!
                                activities.add(activity!!)
                            }
                            loadData()
                        } catch (e: Exception) {
                            dismissProgressDialog()
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
        val adapter =
            ActivitiesListAdapter(activities, object : ActivitiesListAdapter.OnDeleteClickListener {
                override fun onDelete(item: Activity) {
                    activity?.alert(Appcompat, getString(R.string.delete_activity_confirmation)) {
                        yesButton {
                            if (!isNetworkAccessible(context!!)) {
                                showNoInternetConnectionSnackBar()
                                return@yesButton
                            }
                            showProgressDialog()
                            firebaseDatabase.getReference("activities/${firebaseAuth.currentUser?.uid}")
                                .child(item.id).removeValue().addOnFailureListener {
                                    toast(it.localizedMessage)
                                    dismissProgressDialog()

                                }.addOnCompleteListener {
                                    toast(getString(R.string.activity_delete_successful_message))
                                    fetchProgress()
                                }
                        }
                        noButton {

                        }
                    }!!.show()
                }
            })
        rvActivities.layoutManager = LinearLayoutManager(context)
        rvActivities.adapter = adapter
    }
}