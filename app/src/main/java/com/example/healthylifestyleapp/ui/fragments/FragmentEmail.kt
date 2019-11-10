package com.example.healthylifestyleapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.User
import com.example.healthylifestyleapp.ui.activities.AddVitalsDetailsActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.utils.hide
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_email.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class FragmentEmail : BaseFragment(), View.OnClickListener {
    override fun getRoot(): View? {
        return rootView
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppCompatButtonSignUp.setOnClickListener(this)
    }

    @SuppressLint("RestrictedApi")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.AppCompatButtonSignUp -> {
                val email = AppCompatEditTextEmail.text!!.toString().trim { it <= ' ' }
                val username = AppCompatEditTextUserName.text!!.toString().trim { it <= ' ' }
                val password = AppCompatEditTextPassword.text!!.toString().trim { it <= ' ' }
                if (TextUtils.isEmpty(email)) {
                    toast("Please enter email address")
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    toast("Please enter a valid password")
                    return
                }

                if (password.length < 6) {
                    toast("Password too short, enter minimum 6 characters")
                    return
                }

                if (TextUtils.isEmpty(username)) {
                    toast("Please enter a username")
                    return
                }

                showProgressDialog()
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity!!) { task ->

                        if (!task.isSuccessful) {
                            toast("Error occurred while signing you in!")
                            Crashlytics.logException(task.exception)
                        } else {
                            toast("Sign in successful!")
                            val currentUser = firebaseAuth.currentUser
                            val reference = FirebaseDatabase.getInstance().getReference("users")
                            val user = User(
                                email = currentUser?.email,
                                uid = currentUser!!.uid,
                                username = username
                            )
                            reference.child(firebaseAuth.currentUser!!.uid).setValue(user)
                                .addOnCompleteListener {
                                    dismissProgressDialog()
                                    startActivity<AddVitalsDetailsActivity>()
                                    activity?.finish()
                                }

                        }
                    }
            }
        }
    }


    override fun onResume() {
        super.onResume()
        progressBar.hide()
    }


}

















