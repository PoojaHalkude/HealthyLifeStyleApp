package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import com.example.healthylifestyleapp.utils.Constatnts
import com.example.healthylifestyleapp.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forget_password.*
import java.util.regex.Pattern

class ForgetPasswordActivity : AppCompatActivity() {
    internal var EditTextNewPass: AppCompatEditText? = null
    private var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        // EditTextNewPass=findViewById(R.id.EditTextNewPass);

        mAuth = FirebaseAuth.getInstance()

        AppCompatButtonUpdate.setOnClickListener {
            val userEmail = EditTextForegtEmail.text!!.toString()
            //  String pass = EditTextNewPass.getText().toString();
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null) {
                //Code for normal Password
                /*user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgetPasswordActivity.this, "Password changed...", Toast.LENGTH_SHORT).show();
                                Intent newPass= new Intent(ForgetPasswordActivity.this, UserProfileActivity.class);
                                startActivity(newPass);
                            }
                            else
                            {
                                Toast.makeText(ForgetPasswordActivity.this, "password could not be changed...", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });*/


                // Pattern for email id validation
                val p = Pattern.compile(Constatnts.regEx)

                // Match the pattern
                val m = p.matcher(userEmail)

                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(
                        this@ForgetPasswordActivity,
                        "Please Enter your valid email...",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    mAuth!!.sendPasswordResetEmail(userEmail)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Please Check your email account if you want to reset your password",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(
                                    Intent(
                                        this@ForgetPasswordActivity,
                                        MainActivity::class.java
                                    )
                                )
                            } else {
                                val errorMsg = task.exception!!.message
                                Toast.makeText(
                                    this@ForgetPasswordActivity,
                                    "Error Occured: $errorMsg",
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }
                }
            }
        }

    }
}


