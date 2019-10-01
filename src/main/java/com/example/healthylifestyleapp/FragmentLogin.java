package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FragmentLogin extends android.app.Fragment implements View.OnClickListener {

    AppCompatEditText AppCompatEditTextUserName, AppCompatEditTextPassword;
    AppCompatButton AppCompatButtonLogIn;
    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
   // private ProgressDialog progressDialog;
   ProgressBar progressBar;

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);

        AppCompatEditTextUserName = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextUserName);
        AppCompatEditTextPassword = (AppCompatEditText) rootView.findViewById(R.id.AppCompatEditTextPassword);
        AppCompatButtonLogIn = (AppCompatButton) rootView.findViewById(R.id.AppCompatButtonLogIn);

        progressBar = rootView.findViewById(R.id.progressBar);

        //attaching click listener
        AppCompatButtonLogIn.setOnClickListener(this);

//getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(getActivity());

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        /*if (firebaseAuth.getCurrentUser() != null) {
            //close this activity
            getActivity().finish();
            //opening profile activity
            //startActivity(new Intent(getActivity(), StartedActivity.class));


        }*/

        return rootView;
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AppCompatButtonLogIn:

                String username=AppCompatEditTextUserName.getText().toString().trim();
                String password = AppCompatEditTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

               /* if (password.length() < 6) {
                    Toast.makeText(getActivity(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }
*/
                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getActivity(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
               /* firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(),"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Enter Correct Username and Password" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent myIntent=new Intent(getActivity(), ProfileScreenActivity.class);
                                    startActivity(myIntent);

                                   *//* return inflater.inflate(
                                            R.layout.fragment_login, container, false);*//*
                                    // startActivity(new Intent(getActivity(), MainActivity.class));
                                }


                            }
                        });*/

                firebaseAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(),"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Enter Correct Username and Password" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent myIntent=new Intent(getActivity(), StartedActivity.class);
                                    startActivity(myIntent);

                                    // startActivity(new Intent(getActivity(), MainActivity.class));
                                }


                            }
                        });

        }


    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }

}