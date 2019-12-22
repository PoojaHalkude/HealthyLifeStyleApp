package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import kotlinx.android.synthetic.main.activity_feedback_form.*
import org.jetbrains.anko.toast

class FeedbackFormActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feedback_form)
        initialize()
    }

    private fun initialize() {
        setToolbar()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        btnSendFeedback.setOnClickListener {
            submitFeedback()
        }
    }

    private fun submitFeedback() {
        if (!isNetworkAccessible(this)) {
            showNoInternetConnectionSnackBar()
            return
        }
        if (TextUtils.isEmpty(etFeedback.text.toString())) {
            etFeedback.error = getString(R.string.please_fill_the_details)
            return
        }
        uploadFeedback()
    }

    private fun uploadFeedback() {
        showProgressDialog()
        firebaseDatabase.getReference("feedbacks").push()
            .setValue("${firebaseAuth.currentUser?.uid} : ${etFeedback.text}")
            .addOnFailureListener {
                dismissProgressDialog()
                toast(it.localizedMessage)
            }.addOnCompleteListener {
                dismissProgressDialog()
                if (it.isSuccessful) {
                    toast(getString(R.string.feedback_submitted_successfully))
                    finish()
                }
            }
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.Label_Feedback)
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
