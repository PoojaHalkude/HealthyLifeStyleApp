package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.firebase.auth.FirebaseAuth;


public class FragmentPhone extends android.app.Fragment implements View.OnClickListener

{
   AppCompatEditText AppCompatEditTextPhone,AppCompatEditTextUserName,AppCompatEditTextPassword,AppCompatEditTextVerficationCode;
    AppCompatButton buttonSignUpPhone,buttonVerifiactionCode;
    FirebaseAuth mAuth;
    String codeSent="",phone="";
    String no;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment;
        View rootView = inflater.inflate(R.layout.fragment_phone, container, false);
        mAuth = FirebaseAuth.getInstance();
        AppCompatEditTextPhone = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextPhone);
        AppCompatEditTextVerficationCode = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextVerficationCode);

        AppCompatEditTextUserName = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextUserName);
        AppCompatEditTextPassword = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextPassword);
        buttonSignUpPhone = (AppCompatButton) rootView.findViewById(R.id.buttonSignUpPhone);
        buttonVerifiactionCode = (AppCompatButton) rootView.findViewById(R.id.buttonVerifiactionCode);
        //buttonSignUpPhone.setOnClickListener(this);
        buttonVerifiactionCode.setOnClickListener(this);
        return rootView;
    }




    //@Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonVerifiactionCode:
                verifyNo(no);
                break;


        }

    }

    private void verifyNo(String no) {
        no = AppCompatEditTextPhone.getText().toString();
        if(no.isEmpty() || no.length() < 10){
            AppCompatEditTextPhone.setError("Enter a valid mobile");
            AppCompatEditTextPhone.requestFocus();

            return;

        }
        Intent intent = new Intent(getActivity(),VerificationActivity.class);
        intent.putExtra("mobile",no);
        startActivity(intent);
        Toast.makeText(getActivity(), "valid no", Toast.LENGTH_SHORT).show();
    }

   }
