package com.example.healthylifestyleapp.ui.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.ForgotPasswordActivity
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.utils.next
import kotlinx.android.synthetic.main.fragment_login.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class FragmentLogin : BaseFragment(), View.OnClickListener {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //attaching click listener
        AppCompatButtonLogIn.setOnClickListener(this)

        AppCompatTextViewForgotPassword.setOnClickListener {
            activity?.finish()
            startActivity<ForgotPasswordActivity>()
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.AppCompatButtonLogIn -> {

                val username = AppCompatEditTextUserName.text!!.toString().trim { it <= ' ' }
                val password = AppCompatEditTextPassword.text!!.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(activity, "Enter password!", Toast.LENGTH_SHORT).show()
                    return
                }
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(activity, "Enter Username!", Toast.LENGTH_SHORT).show()
                    return
                }


                showProgressDialog()
                firebaseAuth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(activity!!) { task ->
                        Toast.makeText(
                            activity,
                            "createUserWithEmail:onComplete:" + task.isSuccessful,
                            Toast.LENGTH_LONG
                        ).show()

                        dismissProgressDialog()
                        if (task.isSuccessful) {
                            Toast.makeText(activity, "Login Successful!", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.GONE
                            (activity as BaseActivity).next()
                        }
                    }
                    .addOnFailureListener {
                        dismissProgressDialog()
                        toast(it.message!!)
                        it.printStackTrace()
                        Crashlytics.logException(it)
                    }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }

}