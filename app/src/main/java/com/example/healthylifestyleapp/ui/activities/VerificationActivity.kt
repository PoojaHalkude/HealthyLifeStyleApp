package com.example.healthylifestyleapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.User
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_forget_password.*
import kotlinx.android.synthetic.main.activity_verification.*
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

class VerificationActivity : BaseActivity() {
    override fun getRoot(): View {
        return rootView
    }

    private var username: String? = null
    private var mVerificationId: String? = null
    private lateinit var sp: SharedPreferences

    private val mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

            //Getting the code sent by SMS
            val code = phoneAuthCredential.smsCode

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                AppCompatEditTextVerficationCode.setText(code)
                //verifying the code
                verifyVerificationCode(code)
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@VerificationActivity, e.message, Toast.LENGTH_LONG).show()
        }

        override fun onCodeSent(
            s: String,
            forceResendingToken: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(s, forceResendingToken)

            //storing the verification id that is sent to the user
            mVerificationId = s
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification)
        sp = getSharedPreferences("login", Context.MODE_PRIVATE)


        val no = intent.getStringExtra("mobile")
        username = intent.getStringExtra("username")

        sendVerificationCode(no!!)


        buttonSignUpPhone.setOnClickListener(View.OnClickListener {
            sp.edit().putBoolean("logged", true).apply()
            val code = AppCompatEditTextVerficationCode.text!!.toString().trim { it <= ' ' }
            if (code.isEmpty() || code.length < 6) {
                AppCompatEditTextVerficationCode.error = "Enter valid code"
                AppCompatEditTextVerficationCode.requestFocus()
                return@OnClickListener
            }

            //verifying the code entered manually
            verifyVerificationCode(code)
            val intent = Intent(this@VerificationActivity, ConfirmDetailsActivity::class.java)
            startActivity(intent)
        })
    }

    private fun sendVerificationCode(no: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            "+91$no",
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallbacks
        )
    }

    private fun verifyVerificationCode(code: String?) {
        //creating the credential
        val credential = PhoneAuthProvider.getCredential(mVerificationId!!, code!!)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        showProgressDialog()
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this@VerificationActivity) { task ->

                if (task.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    val reference = FirebaseDatabase.getInstance().getReference("users")
                    val user = User(
                        email = currentUser?.email,
                        uid = currentUser!!.uid,
                        phoneNumber = currentUser.phoneNumber,
                        username = username
                    )
                    reference.child(firebaseAuth.currentUser!!.uid).setValue(user)
                        .addOnCompleteListener {
                            //verification successful we will start the profile activity
                            dismissProgressDialog()
                            val intent =
                                Intent(
                                    this@VerificationActivity,
                                    ConfirmDetailsActivity::class.java
                                )
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                            finish()
                        }
                } else {
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        toast("Please enter a valid verification code")
                    } else {
                        toast("Error occurred while verifying your mobile number")
                    }
                }
            }
    }
}
