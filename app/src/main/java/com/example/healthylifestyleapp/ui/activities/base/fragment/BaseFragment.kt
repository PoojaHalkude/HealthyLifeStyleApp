package com.example.healthylifestyleapp.ui.activities.base.fragment


import android.view.View
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.customview.UniversalLoader
import com.example.healthylifestyleapp.utils.SnackBarActionListener
import com.example.healthylifestyleapp.utils.showSnackBar
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {
    private var rootView: View? = null
    private var dialog: UniversalLoader? = null
    protected val firebaseAuth = FirebaseAuth.getInstance()
    abstract fun getRoot(): View?
    protected val firebaseDatabase = FirebaseDatabase.getInstance()
    fun showProgressDialog() {

        if (dialog == null) {
            dialog = UniversalLoader(this.activity!!)
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
