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

public class ForgetPasswordActivity extends AppCompatActivity {
    AppCompatEditText EditTextForegtEmail;
    AppCompatButton AppCompatButtonSendEmail;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        AppCompatButtonSendEmail = findViewById(R.id.AppCompatButtonSendEmail);
        EditTextForegtEmail = findViewById(R.id.EditTextForegtEmail);
        mAuth = FirebaseAuth.getInstance();
        //  EditTextForegtEmail.setOnClickListener(this);
        AppCompatButtonSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = AppCompatButtonSendEmail.getText().toString();
                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(ForgetPasswordActivity.this, "Please Enter your valid email...", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    mAuth.sendPasswordResetEmail(userEmail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgetPasswordActivity.this, "Please Check your email account if you want to reset your password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgetPasswordActivity.this,MainActivity.class));
                            } else {
                                String errorMsg=task.getException().getMessage();
                                Toast.makeText(ForgetPasswordActivity.this, "Error Occured: "+errorMsg, Toast.LENGTH_LONG).show();

                            }
                        }
                    });
                }
            }
        });
    }
}


