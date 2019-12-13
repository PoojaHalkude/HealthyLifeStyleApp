@file:Suppress(
    "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS",
    "RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS"
)

package com.example.healthylifestyleapp.utils

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

object DateExtensions {


    const val YYYY_MM_DD = "yyyy-MM-dd"
    const val yyyy_MM_dd_T_HH_mm_ss_SSSz = "E MMM dd HH:mm:ss z yyyy"
    const val yyyy_MM_dd_T_HH_mm_ss_00z = "yyyy-MM-dd'T'HH:mm:ss\'Z\'"
    const val D_MMM_YYYY = "d MMM, yyyy"
    const val D_MMM_YYYY_AT_HH_MM_A = "d MMM, yyyy 'at' hh:mm a"
    const val MMM_YYYY = "MMM yyyy"

    data class Result(val result: String?, val date: Date? = null)

    interface OnDateSetListener {
        fun onDateSet(datetime: String, date: Date)
    }

    @SuppressLint("SimpleDateFormat")
    fun showDatePicker(
        context: Context,
        dateFormat: String = D_MMM_YYYY_AT_HH_MM_A,
        onDateSetListener: OnDateSetListener,
        allowFutureDate: Boolean = false,
        minDate: Long = 0
    ) {
        val cal: Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            context,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val sdf = SimpleDateFormat(dateFormat)
                val time = cal.time
                onDateSetListener.onDateSet(sdf.format(time), time)

            },
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )
        if (!allowFutureDate) {
            datePicker.datePicker.maxDate = System.currentTimeMillis()
        }
        if (minDate > 0) {
            datePicker.datePicker.minDate = minDate
        }

        datePicker.show()
    }

    @SuppressLint("SimpleDateFormat")
    fun format(date: Date, format: String = D_MMM_YYYY_AT_HH_MM_A): String? {
        val sdf = SimpleDateFormat(format)
        return sdf.format(date)
    }

}