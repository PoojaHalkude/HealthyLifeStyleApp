package com.example.healthylifestyleapp;

import android.annotation.SuppressLint;
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

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class FragmentEmail extends android.app.Fragment implements View.OnClickListener {
    AppCompatButton AppCompatButtonSignUp;
    AppCompatEditText AppCompatEditTextEmail,AppCompatEditTextUserName,AppCompatEditTextPassword;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;



    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_email, container, false);


        AppCompatButtonSignUp = (AppCompatButton) rootView.findViewById(R.id.AppCompatButtonSignUp);
        AppCompatEditTextEmail=rootView.findViewById(R.id.AppCompatEditTextEmail);
        AppCompatEditTextPassword=rootView.findViewById(R.id.AppCompatEditTextPassword);
        AppCompatEditTextUserName=rootView.findViewById(R.id.AppCompatEditTextUserName);
        progressBar=rootView.findViewById(R.id.progressBar);
        FirebaseApp.initializeApp(getActivity());
        mAuth = FirebaseAuth.getInstance();
      /*  if(mAuth.getCurrentUser() != null){
            //close this activity
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }*/

      /*  if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(getActivity(), UserProfileActivity.class));
        }*/
        FirebaseApp.initializeApp(getActivity());
        AppCompatButtonSignUp.setOnClickListener(this);
        return rootView;

    }
    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.AppCompatButtonSignUp:
                String email = AppCompatEditTextEmail.getText().toString().trim();
                String username=AppCompatEditTextUserName.getText().toString().trim();
                String password = AppCompatEditTextPassword.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    Toast.makeText(getApplicationContext(), "Enter Username!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);


                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(getActivity(),"createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Registration Successful!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                    Intent myIntent=new Intent(getActivity(), UserProfileActivity.class);
                                    startActivity(myIntent);

                                    // startActivity(new Intent(getActivity(), MainActivity.class));
                                }


                            }
                        });
              /*  mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getActivity(), "Registration successful!", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);

                                    Intent intent = new Intent(getActivity(), UpdateProfileDataActivity.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });*/
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
    @Override

    public void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser()!=null)
        {
            getActivity().finish();
            startActivity(new Intent(getActivity(),ProfileScreenActivity.class ));

        }
    }


}













