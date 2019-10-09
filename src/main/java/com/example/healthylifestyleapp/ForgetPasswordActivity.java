package com.example.healthylifestyleapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {
    AppCompatEditText EditTextForegtEmail,EditTextNewPass;
    AppCompatButton AppCompatButtonUpdate;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        AppCompatButtonUpdate = findViewById(R.id.AppCompatButtonUpdate);
        EditTextForegtEmail = findViewById(R.id.EditTextForegtEmail);
       // EditTextNewPass=findViewById(R.id.EditTextNewPass);

        mAuth = FirebaseAuth.getInstance();

        AppCompatButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = EditTextForegtEmail.getText().toString();
              //  String pass = EditTextNewPass.getText().toString();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    //Code for normal Password
                    /*user.updatePassword(pass).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(ForgetPasswordActivity.this, "Password changed...", Toast.LENGTH_SHORT).show();
                                Intent newPass= new Intent(ForgetPasswordActivity.this, UserProfileActivity.class);
                                startActivity(newPass);
                            }
                            else
                            {
                                Toast.makeText(ForgetPasswordActivity.this, "password could not be changed...", Toast.LENGTH_SHORT).show();

                            }

                        }
                    });*/


                    // Pattern for email id validation
                    Pattern p = Pattern.compile(Constatnts.regEx);

                    // Match the pattern
                    Matcher m = p.matcher(userEmail);

                    if (TextUtils.isEmpty(userEmail)) {
                        Toast.makeText(ForgetPasswordActivity.this, "Please Enter your valid email...", Toast.LENGTH_SHORT).show();
                    } else {
                        mAuth.sendPasswordResetEmail(userEmail)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(ForgetPasswordActivity.this, "Please Check your email account if you want to reset your password", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(ForgetPasswordActivity.this, MainActivity.class));
                                        } else {
                                            String errorMsg = task.getException().getMessage();
                                            Toast.makeText(ForgetPasswordActivity.this, "Error Occured: " + errorMsg, Toast.LENGTH_LONG).show();

                                        }
                                    }
                                });
                    }
                }
            }
});

    }
}


