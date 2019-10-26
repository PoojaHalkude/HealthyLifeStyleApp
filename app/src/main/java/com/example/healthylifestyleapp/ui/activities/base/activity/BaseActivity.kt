package com.example.healthylifestyleapp.ui.activities.base.activity

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.customview.UniversalLoader
import com.example.healthylifestyleapp.utils.SnackBarActionListener
import com.example.healthylifestyleapp.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

public abstract class BaseActivity : AppCompatActivity() {
    protected val firebaseAuth = FirebaseAuth.getInstance()
    protected val firebaseDatabase = FirebaseDatabase.getInstance()
    private lateinit var rootView: View
    private var dialog: UniversalLoader? = null

    abstract fun getRoot(): View

    fun showProgressDialog() {

        if (dialog == null) {
            dialog = UniversalLoader(this)
            dialog!!.setCancelable(false)
            dialog!!.show()
        } else {
            if (!dialog!!.isShowing) {
                dialog!!.show()
            }
        }
    }

    fun dismissProgressDialog() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun showNoInternetConnectionSnackBar() {
        rootView = getRoot()
        if (rootView == null) {
            return
        }
        showSnackBar(
            rootView, getString(
                R.string.no_internet_connection
            )
        )
    }

    fun showShortSnackBar(
        resId: Int,
        action: Int? = null,
        listener: SnackBarActionListener? = null
    ) {
        rootView = getRoot()
        if (rootView == null) {
            return
        }
        showSnackBar(
            rootView,
            getString(resId),
            action,
            Snackbar.LENGTH_SHORT,
            listener
        )
    }

    fun showLongSnackBar(
        resId: Int,
        action: Int? = null,
        listener: SnackBarActionListener? = null
    ) {
        rootView = getRoot()
        if (rootView == null) {
            return
        }
        showSnackBar(
            rootView,
            getString(resId),
            action,
            Snackbar.LENGTH_LONG,
            listener
        )
    }

    fun showIndefiniteSnackBar(
        resId: Int,
        action: Int? = null,
        listener: SnackBarActionListener? = null
    ) {
        rootView = getRoot()
        if (rootView == null) {
            return
        }
        showSnackBar(
            rootView,
            getString(resId),
            action,
            Snackbar.LENGTH_INDEFINITE,
            listener
        )
    }
}