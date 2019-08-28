package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;


public class FragmentPhone extends android.app.Fragment implements View.OnClickListener {
   AppCompatEditText AppCompatEditTextPhone,AppCompatEditTextUserName,AppCompatEditTextPassword,AppCompatEditTextVerficationCode;
    AppCompatButton buttonSignUpPhone,buttonVerifiactionCode;
    FirebaseAuth mAuth;
    String codeSent="";


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment;
        View rootView = inflater.inflate(R.layout.fragment_phone, container, false);

    mAuth=FirebaseAuth.getInstance();
        AppCompatEditTextPhone = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextPhone);
        AppCompatEditTextVerficationCode = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextVerficationCode);

        AppCompatEditTextUserName = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextUserName);
        AppCompatEditTextPassword = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextPassword);
        buttonSignUpPhone=(AppCompatButton)rootView.findViewById(R.id.buttonSignUpPhone);
        buttonVerifiactionCode=(AppCompatButton)rootView.findViewById(R.id.buttonVerifiactionCode);
        buttonSignUpPhone.setOnClickListener(this);
        buttonVerifiactionCode.setOnClickListener(this);
        return  rootView;

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.buttonVerifiactionCode:
                sendVerificationCode();
                break;
            case R.id.buttonSignUpPhone:
                verifySignInCode();


        }

    }

    private void verifySignInCode() {
        String code= AppCompatEditTextVerficationCode.getText().toString();
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(codeSent, code);
        signInWithPhoneAuthCredential(credential);

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Sign in success, update UI with the signed-in user's information
                            // here you can open new activity
                          Intent intent = new Intent(getActivity(), StartedActivity.class);
                       startActivity(intent);
                            Toast.makeText(getActivity(), "Login Sucessfull", Toast.LENGTH_SHORT).show();

                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                                Toast.makeText(getActivity(), "Incorrect verification code", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
    }

    private void sendVerificationCode() {
        String phone=AppCompatEditTextPhone.getText().toString();
        if (phone.isEmpty())
        {
            AppCompatEditTextPhone.setError("Phone no is required");
            AppCompatEditTextPhone.requestFocus();
            return;

        }
        if(phone.length()<10)
        {
            AppCompatEditTextPhone.setError("Please enter a valid phone");
            AppCompatEditTextPhone.requestFocus();
            return;
        }
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                (Executor) this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent=s;
        }
    };
}
