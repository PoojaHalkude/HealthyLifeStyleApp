package com.example.healthylifestyleapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.StartedActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_login.*

class FragmentLogin : Fragment(), View.OnClickListener {


    //firebase auth object
    private var firebaseAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_login, container, false)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //attaching click listener
        AppCompatButtonLogIn.setOnClickListener(this)

        //getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(context!!.applicationContext)
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

                progressBar.visibility = View.VISIBLE
                firebaseAuth!!.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(activity!!) { task ->
                        Toast.makeText(
                            activity,
                            "createUserWithEmail:onComplete:" + task.isSuccessful,
                            Toast.LENGTH_LONG
                        ).show()

                        progressBar.visibility = View.GONE

                        if (!task.isSuccessful) {
                            Toast.makeText(
                                activity,
                                "Please Enter Correct Username and Password " + task.exception!!,
                                Toast.LENGTH_LONG
                            ).show()
                        } else {
                            Toast.makeText(activity, "Login Successful!", Toast.LENGTH_LONG).show()
                            progressBar.visibility = View.GONE
                            val myIntent = Intent(activity, StartedActivity::class.java)
                            startActivity(myIntent)

                            // startActivity(new Intent(getActivity(), MainActivity.class));
                        }
                    }
            }
        }


    }

    override fun onResume() {
        super.onResume()
        progressBar.visibility = View.GONE
    }

}