package com.example.healthylifestyleapp.ui.activities.base.activity

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

public abstract class BaseActivity : AppCompatActivity() {
    protected val firebaseAuth = FirebaseAuth.getInstance()
    protected val firebaseDatabase = FirebaseDatabase.getInstance()
}