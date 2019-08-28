package com.example.healthylifestyleapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
/*import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;*/
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatTextView AppCompatTextViewSignUp;
    AppCompatButton appCompatButtonSignUpEmail,appCompatButtonSignUpPhone,AppCompatButtonSignUp;
    private AppCompatEditText AppCompatEditTextEmail, AppCompatEditTextPassword,AppCompatEditTextUserName;

 private FirebaseAuth auth;
 Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniUI();
        initListner();
        //Get Firebase auth instance
//      auth = FirebaseAuth.getInstance();
//        FirebaseApp.initializeApp(context);

    }
   /* @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        updateUI(currentUser);
    }
*/
    private void initListner() {
        AppCompatTextViewSignUp.setOnClickListener(this);
    }

    private void iniUI() {
        AppCompatTextViewSignUp=findViewById(R.id.AppCompatTextViewSignUp);
        appCompatButtonSignUpEmail=findViewById(R.id.appCompatButtonSignUpEmail);
        appCompatButtonSignUpPhone=findViewById(R.id.appCompatButtonSignUpPhone);
        AppCompatEditTextEmail=findViewById(R.id.AppCompatEditTextEmail);
        AppCompatEditTextPassword=findViewById(R.id.AppCompatEditTextPassword);
        AppCompatEditTextUserName=findViewById(R.id.AppCompatEditTextUserName);



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
    }


}
