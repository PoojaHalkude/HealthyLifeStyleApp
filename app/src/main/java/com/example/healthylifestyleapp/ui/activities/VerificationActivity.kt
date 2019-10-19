package com.example.healthylifestyleapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.android.synthetic.main.activity_verification.*
import java.util.concurrent.TimeUnit

class VerificationActivity : AppCompatActivity() {

    private var mAuth: FirebaseAuth? = null
    private var mVerificationId: String? = null
    internal var context: Context = this
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
        FirebaseApp.initializeApp(context)
        sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        mAuth = FirebaseAuth.getInstance()

        val no = intent.getStringExtra("mobile")

        sendVerificationCode(no!!)


        buttonSignUpPhone.setOnClickListener(View.OnClickListener {
            goToMainActivity()
            sp.edit().putBoolean("logged", true).apply()
            val code = AppCompatEditTextVerficationCode.text!!.toString().trim { it <= ' ' }
            if (code.isEmpty() || code.length < 6) {
                AppCompatEditTextVerficationCode.error = "Enter valid code"
                AppCompatEditTextVerficationCode.requestFocus()
                return@OnClickListener
            }

            //verifying the code entered manually
            verifyVerificationCode(code)
            val intent = Intent(this@VerificationActivity, StartedActivity::class.java)
            startActivity(intent)
        })
    }

    private fun goToMainActivity() {
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
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

        //signing the user
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener(this@VerificationActivity) { task ->
                if (task.isSuccessful) {
                    //verification successful we will start the profile activity
                    val intent = Intent(this@VerificationActivity, StartedActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                } else {

                    //verification unsuccessful.. display an error message

                    var message = "Somthing is wrong, we will fix it soon..."

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered..."
                    }


                }
            }
    }
}
