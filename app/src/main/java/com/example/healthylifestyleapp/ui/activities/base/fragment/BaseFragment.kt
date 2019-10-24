package com.example.healthylifestyleapp.ui.activities.base.fragment


import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

/**
 * A simple [Fragment] subclass.
 */
abstract class BaseFragment : Fragment() {
    protected val firebaseAuth = FirebaseAuth.getInstance()
    protected val firebaseDatabase = FirebaseDatabase.getInstance()
}
