package com.example.healthylifestyleapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatTextView AppCompatTextViewSignUp;
    AppCompatButton appCompatButtonSignUpEmail,appCompatButtonSignUpPhone,AppCompatButtonSignUp;
    private AppCompatEditText AppCompatEditTextEmail, AppCompatEditTextPassword,AppCompatEditTextUserName;

    private ProgressBar progressBar;
 private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iniUI();
        initListner();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

    }


    private void initListner() {
        AppCompatTextViewSignUp.setOnClickListener(this);
        AppCompatButtonSignUp.setOnClickListener(this);
    }

    private void iniUI() {
        AppCompatTextViewSignUp=findViewById(R.id.AppCompatTextViewSignUp);
        appCompatButtonSignUpEmail=findViewById(R.id.appCompatButtonSignUpEmail);
        appCompatButtonSignUpPhone=findViewById(R.id.appCompatButtonSignUpPhone);
        AppCompatEditTextEmail=findViewById(R.id.AppCompatEditTextEmail);
        AppCompatEditTextPassword=findViewById(R.id.AppCompatEditTextPassword);
        AppCompatEditTextUserName=findViewById(R.id.AppCompatEditTextUserName);
        AppCompatButtonSignUp=findViewById(R.id.AppCompatButtonSignUp);
        progressBar=findViewById(R.id.progressBar);

    }


    @Override
    public void onClick(View v) {

        Fragment fr;
        switch ((v.getId()))
        {
            case R.id.AppCompatTextViewSignUp:
                fr = new FragmentSignUp();
                break;
            case R.id.appCompatButtonSignUpEmail:
                fr=new FragmentEmail();
                break;
            case R.id.appCompatButtonSignUpPhone:
                fr=new FragmentPhone();
                break;
            case R.id.AppCompatTextViewLogin:
                fr=new FragmentLogin();
                break;
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

                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(MainActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();
                                }


                            }
                        });

            default:
                return;

        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();



    }




    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }


}
