package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Goals
import com.example.healthylifestyleapp.model.Water
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import kotlinx.android.synthetic.main.activity_goals.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class GoalsActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goals)
        initialize()
    }

    private fun initialize() {
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btnFinish.setOnClickListener {
            submitGoals()
        }
    }

    private fun submitGoals() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (!isFormValid()) {
            return
        }
        showProgressDialog()
        firebaseDatabase.getReference("goals").child(firebaseAuth.currentUser!!.uid)
            .setValue(
                Goals(
                    water = Water("litres", etWater.text.toString().toInt()),
                    sleep = etSleep.text.toString().toInt(),
                    food = etFood.text.toString().toInt()
                )
            ).addOnCompleteListener {
                dismissProgressDialog()
                if (it.isSuccessful) {
                    startActivity<UserProfileActivity>()
                    finish()
                } else {
                    toast("Failed to save your data. Please try again!")
                }
            }
            .addOnFailureListener {
                dismissProgressDialog()
                toast(it.message!!)
            }
    }

    private fun isFormValid(): Boolean {
        var valid = true
        val errorMessage = ArrayList<String>()

        if (TextUtils.isEmpty(etWater.text.toString()) || !TextUtils.isDigitsOnly(etWater.text.toString())) {
            errorMessage.add("water")
            valid = false
        }

        if (TextUtils.isEmpty(etSleep.text.toString()) || !TextUtils.isDigitsOnly(etSleep.text.toString())) {
            errorMessage.add("sleep")
            valid = false
        }

        if (TextUtils.isEmpty(etFood.text.toString()) || !TextUtils.isDigitsOnly(etFood.text.toString())) {
            errorMessage.add("food")
            valid = false
        }
        if (errorMessage.isNotEmpty()) toast(
            "Please enter number values for: ${errorMessage.joinToString(
                ","
            ) { it }}"
        )
        return valid
    }
}