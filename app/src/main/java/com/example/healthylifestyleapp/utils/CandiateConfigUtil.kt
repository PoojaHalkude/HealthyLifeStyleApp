package com.example.healthylifestyleapp.utils

import com.example.healthylifestyleapp.model.Goals
import com.example.healthylifestyleapp.model.Physique
import com.example.healthylifestyleapp.ui.activities.AddVitalsDetailsActivity
import com.example.healthylifestyleapp.ui.activities.GoalsActivity
import com.example.healthylifestyleapp.ui.activities.OnBoardingOptionsActivity
import com.example.healthylifestyleapp.ui.activities.UserProfileActivity
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.firebase.client.FirebaseError
import com.firebase.client.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import org.jetbrains.anko.startActivity

fun BaseActivity.next() {
    when {
        firebaseAuth.currentUser != null -> {
            firebaseDatabase.getReference("physiques/${firebaseAuth.currentUser!!.uid}")
                .addListenerForSingleValueEvent(object : ValueEventListener,
                    com.google.firebase.database.ValueEventListener {
                    override fun onCancelled(p0: FirebaseError?) {

                    }

                    override fun onDataChange(p0: com.firebase.client.DataSnapshot?) {

                    }

                    override fun onCancelled(p0: DatabaseError) {

                    }

                    override fun onDataChange(p0: DataSnapshot) {
                        if (p0.getValue(Physique::class.java) != null) {
                            firebaseDatabase.getReference("goals/${firebaseAuth.currentUser!!.uid}")
                                .addListenerForSingleValueEvent(object : ValueEventListener,
                                    com.google.firebase.database.ValueEventListener {
                                    override fun onCancelled(p0: FirebaseError?) {

                                    }

                                    override fun onCancelled(p0: DatabaseError) {

                                    }

                                    override fun onDataChange(p0: com.firebase.client.DataSnapshot?) {

                                    }

                                    override fun onDataChange(p0: DataSnapshot) {
                                        if (p0.getValue(Goals::class.java) != null) {
                                            startActivity<UserProfileActivity>()
                                            finish()
                                        } else {
                                            startActivity<GoalsActivity>()
                                            finish()
                                        }
                                    }
                                })
                        } else {
                            startActivity<AddVitalsDetailsActivity>()
                            finish()
                        }
                    }
                })
        }
        else -> {
            startActivity<OnBoardingOptionsActivity>()
            finish()
        }
    }
}
