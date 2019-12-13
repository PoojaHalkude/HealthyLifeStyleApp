package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.DateExtensions
import com.example.healthylifestyleapp.utils.DateExtensions.D_MMM_YYYY_AT_HH_MM_A
import com.example.healthylifestyleapp.utils.isEmpty
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import kotlinx.android.synthetic.main.activity_add_new.*
import org.jetbrains.anko.toast
import java.util.*
import kotlin.collections.ArrayList

class AddNewActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)
        initialize()
    }

    private fun initialize() {
        setToolbar()
        setSpinner()
        setCurrentDate()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btnAddActivity.setOnClickListener {
            submitActivity()
        }
    }

    private fun submitActivity() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (!isFormValid()) {
            showErrors()
            return
        }
        submit()
    }

    private fun submit() {
        val activity = Activity(
            name = etActivityName.text.toString(),
            lastModifiedTimestamp = tvDate.text.toString(),
            quantity = etQuantity.text.toString().toInt(),
            type = spinnerActivity.selectedItem.toString()
        )
        val list: ArrayList<Activity> = ArrayList()
        list.add(activity)
        firebaseDatabase.getReference("activities").child("${firebaseAuth.currentUser?.uid}").push()
            .setValue(activity).addOnCompleteListener {
                if (it.isSuccessful) {
                    finish()
                }
            }.addOnFailureListener {
                toast(it.localizedMessage)
                Crashlytics.log(it.localizedMessage)
            }
    }

    private fun showErrors() {
        if (etActivityName.isEmpty()) {
            etActivityName.error = "Enter activity description"
        } else {
            etActivityName.error = ""
        }

        if (etQuantity.isEmpty()) {
            etQuantity.error = "Enter activity quantity"
        } else {
            etQuantity.error = ""
        }
    }

    private fun isFormValid(): Boolean {
        return !etActivityName.isEmpty() && !etQuantity.isEmpty()
    }

    private fun setCurrentDate() {
        tvDate.text = DateExtensions.format(Calendar.getInstance().time)
        tvDate.setOnClickListener {
            DateExtensions.showDatePicker(
                context = this,
                dateFormat = D_MMM_YYYY_AT_HH_MM_A,
                onDateSetListener = object : DateExtensions.OnDateSetListener {
                    override fun onDateSet(datetime: String, date: Date) {
                        tvDate.text = datetime
                    }
                },
                allowFutureDate = false,
                minDate = 0
            )
        }
    }

    private fun setSpinner() {
        spinnerActivity.adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            android.R.id.text1,
            resources.getStringArray(R.array.activities)
        )
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.Label_AddNewActivity)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black)
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
