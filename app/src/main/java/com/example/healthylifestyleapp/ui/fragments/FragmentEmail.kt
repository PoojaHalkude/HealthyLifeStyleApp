package com.example.healthylifestyleapp.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.MainActivity
import com.example.healthylifestyleapp.ui.activities.StartedActivity
import com.firebase.ui.auth.AuthUI.getApplicationContext
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_email.*

class FragmentEmail : Fragment(), View.OnClickListener {

    private lateinit var mAuth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_email, container, false)



        FirebaseApp.initializeApp(activity!!)
        mAuth = FirebaseAuth.getInstance()

        FirebaseApp.initializeApp(activity!!)

        return rootView

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
                    Toast.makeText(
                        getApplicationContext(),
                        "Enter email address!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT)
                        .show()
                    return
                }

                if (password.length < 6) {
                    Toast.makeText(
                        getApplicationContext(),
                        "Password too short, enter minimum 6 characters!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT)
                        .show()
                    return
                }

                progressBar!!.visibility = View.VISIBLE

                //2nd  code
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(activity!!) { task ->
                        Toast.makeText(
                            activity,
                            "createUserWithEmail:onComplete:" + task.isSuccessful,
                            Toast.LENGTH_SHORT
                        ).show()
                        /* FragmentTransaction ft = getFragmentManager().beginTransaction();
                            Fragment mFrag = new NextFragment();
                            ft.replace(R.id.fra, mFrag);
                            ft.commit();*/
                        progressBar!!.visibility = View.GONE

                        if (!task.isSuccessful) {
                            Toast.makeText(
                                activity, "Authentication failed." + task.exception!!,
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            startActivity(Intent(activity, MainActivity::class.java))
                        }
                        val myIntent = Intent(activity, StartedActivity::class.java)
                        startActivity(myIntent)
                    }
            }
        }/* mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(), "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(getActivity(), MainActivity.class));
                                }


                            }
                        });*//*  Intent myIntent = new Intent(getActivity(), StartedActivity.class);
                startActivity(myIntent);*/
    }


    override fun onResume() {
        super.onResume()
        progressBar!!.visibility = View.GONE
    }


}

















