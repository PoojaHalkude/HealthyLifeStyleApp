package com.example.healthylifestyleapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.VerificationActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_phone.*


class FragmentPhone : Fragment(), View.OnClickListener {

    internal lateinit var mAuth: FirebaseAuth
    private var no: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment;
        val rootView = inflater.inflate(R.layout.fragment_phone, container, false)
        mAuth = FirebaseAuth.getInstance()

        //buttonSignUpPhone.setOnClickListener(this);
        buttonVerifiactionCode.setOnClickListener(this)
        return rootView
    }


    //@Override
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonVerifiactionCode -> verifyNo(no)
        }

    }

    private fun verifyNo(no: String?) {
        var no = no
        no = AppCompatEditTextPhone.text!!.toString()
        if (no.isEmpty() || no.length < 10) {
            AppCompatEditTextPhone.error = "Enter a valid mobile"
            AppCompatEditTextPhone.requestFocus()

            return

        }
        val intent = Intent(activity, VerificationActivity::class.java)
        intent.putExtra("mobile", no)
        startActivity(intent)
        Toast.makeText(activity, "valid no", Toast.LENGTH_SHORT).show()
    }

}
