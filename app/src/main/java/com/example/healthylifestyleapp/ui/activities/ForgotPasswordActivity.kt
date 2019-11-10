package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.utils.Constatnts
import kotlinx.android.synthetic.main.activity_forget_password.*
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class ForgotPasswordActivity : BaseActivity() {
    override fun getRoot(): View? {
        return rootView
    }

    internal var EditTextNewPass: AppCompatEditText? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        // EditTextNewPass=findViewById(R.id.EditTextNewPass);


        AppCompatButtonUpdate.setOnClickListener {
            val userEmail = etForgotPassword.text!!.toString()
            //  String pass = EditTextNewPass.getText().toString();
            val user = firebaseAuth.currentUser
            if (user != null) {
                //Code for normal Password
                /*user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "Password changed...", Toast.LENGTH_SHORT).show();
                                Intent newPass= new Intent(ForgotPasswordActivity.this, UserProfileActivity.class);
                                startActivity(newPass);
                            }
                            else
                            {
                                Toast.makeText(ForgotPasswordActivity.this, "password could not be changed...", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });*/


                // Pattern for email id validation
                val p = Pattern.compile(Constatnts.regEx)

                // Match the pattern
                val m = p.matcher(userEmail)

                if (TextUtils.isEmpty(userEmail) && m.matches()) {
                    toast("Please enter your valid email address")
                } else {
                    firebaseAuth.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    "Please Check your email account if you want to reset your password",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        this@ForgotPasswordActivity,
                                        OnBoardingOptionsActivity::class.java
                                    )
                                )
                            } else {
                                task.exception?.printStackTrace()
                                Crashlytics.logException(task.exception)
                                toast("Failed to send you the reset password link")
                            }
                        }
                }
            }
        }

    }
}


