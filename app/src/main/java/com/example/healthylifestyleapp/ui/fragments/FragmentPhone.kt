package com.example.healthylifestyleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.VerificationActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_phone.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class FragmentPhone : BaseFragment(), View.OnClickListener {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_phone, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonVerifiactionCode.setOnClickListener(this)
    }


    //@Override
    override fun onClick(v: View) {
        when (v.id) {
            R.id.buttonVerifiactionCode -> verifyNo()
        }

    }

    private fun verifyNo() {
        val no = AppCompatEditTextPhone.text!!.toString()

        if (no.isEmpty() || no.length < 10) {
            toast("Enter a valid mobile number")
            AppCompatEditTextPhone.requestFocus()
            return
        }
        startActivity<VerificationActivity>(
            "mobile" to no,
            "username" to AppCompatEditTextUserName.text.toString()
        )
    }

}
