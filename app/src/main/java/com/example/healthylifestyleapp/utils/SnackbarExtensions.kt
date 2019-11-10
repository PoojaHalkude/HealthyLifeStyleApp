package com.example.healthylifestyleapp.utils

import android.view.View
import androidx.core.content.ContextCompat
import com.example.healthylifestyleapp.R
import com.google.android.material.snackbar.Snackbar

/**
 * This interface is used as a callback for a click event
 * on Snackbar action button.
 *
 */
interface SnackBarActionListener {
    /**
     * Event to indicate that the Snackbar action button is clicked.
     *
     * @param message is the message shown in the [Snackbar].
     * @param action resourceId of the action text.
     * @param snackbar [Snackbar] object.
     */
    fun onSnackBarActionClick(message: String, action: Int?, snackbar: Snackbar)
}

fun showSnackBar(
    view: View?,
    message: String,
    action: Int? = null,
    duration: Int = Snackbar.LENGTH_LONG,
    listener: SnackBarActionListener? = null
) {
    if (view == null) {
        return
    }
    val snackBar = Snackbar.make(view, message, duration)
    snackBar.setActionTextColor(ContextCompat.getColor(view.context, R.color.colorPrimaryDark))

    if (action != null) {
        snackBar.setAction(action) {
            listener?.onSnackBarActionClick(message = message, action = action, snackbar = snackBar)
        }
    }
    snackBar.show()
}