package com.example.healthylifestyleapp.ui.activities

/*
import android.support.v7.app.AppCompatActivity;
*/

import android.app.Dialog
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Physique
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.InputRangeFilter
import com.example.healthylifestyleapp.utils.isEmpty
import com.example.healthylifestyleapp.utils.isNetworkAccessible
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_vitals_details.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AddVitalsDetailsActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    private var gender = "Male"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vitals_details)
        initListener()
    }

    private fun initListener() {
        setInputRangeListeners()
        btnMale.isSelected = true
        btnFemale.isSelected = false
        btnMale.setOnClickListener {
            btnMale.isSelected = true
            btnFemale.isSelected = false
            gender = "Male"
        }
        btnFemale.setOnClickListener {
            btnMale.isSelected = false
            btnFemale.isSelected = true
            gender = "Female"
        }
        tvLevel.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_chose_level)
            dialog.show()
            val radioGroup = dialog.findViewById(R.id.RadioGroupLevel) as RadioGroup
            val beginner = dialog.findViewById(R.id.RadioButtonBeginner) as RadioButton
            val advanced = dialog.findViewById(R.id.RadioButtonAdvance) as RadioButton
            val intermediate = dialog.findViewById(R.id.RadioButtonIntermediate) as RadioButton

            radioGroup.setOnCheckedChangeListener { _, _ ->
                when {
                    beginner.isChecked ->
                        tvLevel.text = beginner.text.toString()
                    advanced.isChecked ->
                        tvLevel.text = advanced.text.toString()
                    else ->
                        tvLevel.text = intermediate.text.toString()
                }
                dialog.dismiss()
            }
        }
        btnConfirmDetails.setOnClickListener {
            if (!isNetworkAccessible(this)) {
                toast(R.string.no_internet_connection)
                return@setOnClickListener
            }
            if (isFormValid()) {
                submitPhysiqueDetails()
            }
        }

    }

    private fun submitPhysiqueDetails() {
        if (!isNetworkAccessible(this)) {
            toast(R.string.no_internet_connection)
            return
        }
        showProgressDialog()
        firebaseDatabase.getReference("physiques").child(firebaseAuth.currentUser!!.uid)
            .setValue(
                Physique(
                    age = etAge.text.toString().toInt(),
                    height = etHeight.text.toString().toInt(),
                    weight = etWeight.text.toString().toInt(),
                    level = tvLevel.text.toString(),
                    gender = gender
                )
            ).addOnCompleteListener {
                dismissProgressDialog()
                if (it.isSuccessful) {
                    startActivity<GoalsActivity>()
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

    private fun setInputRangeListeners() {
        etHeight.filters = arrayOf(InputRangeFilter(1, 300))
        etWeight.filters = arrayOf(InputRangeFilter(1, 100))
        etAge.filters = arrayOf(InputRangeFilter(1, 80))
    }

    private fun isFormValid(): Boolean {
        var valid = true
        var error = ""

        if (etAge.isEmpty()) {
            error = error.plus("age ")
            valid = false
        }

        if (etHeight.isEmpty()) {
            error = error.plus("height ")
            valid = false
        }

        if (etWeight.isEmpty()) {
            error = error.plus("weight")
            valid = false
        }
        if (!TextUtils.isEmpty(error)) {
            Snackbar.make(
                btnConfirmDetails,
                "Please enter following values:\n${error.replace(" ", ", ")}",
                Snackbar.LENGTH_LONG
            ).show()
        }
        return valid
    }
}


