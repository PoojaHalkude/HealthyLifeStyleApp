package com.example.healthylifestyleapp.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.crashlytics.android.Crashlytics
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.User
import com.example.healthylifestyleapp.ui.activities.AddVitalsDetailsActivity
import com.example.healthylifestyleapp.ui.activities.ManualLoginActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class FragmentSignUp : BaseFragment() {
    override fun getRoot(): View? {
        return rootView
    }

    private var mGoogleSignInClient: GoogleSignInClient? = null
    private lateinit var callbackManager: CallbackManager
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        //Inflate the layout for this fragment

        return inflater.inflate(
            R.layout.fragment_sign_up, container, false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize() {
        setUp()
        setOnClickListeners()
    }

    private fun setUp() {
        btnSignInGoogle.setSize(SignInButton.SIZE_STANDARD)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestProfile()
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity!!, gso)

        FacebookSdk.sdkInitialize(activity) {
            btnSignInFacebook.setReadPermissions(listOf(ContactsContract.Intents.Insert.EMAIL))
            callbackManager = CallbackManager.Factory.create()
        }

        LoginManager.getInstance()
            .registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(loginResult: LoginResult) {
                    FirebaseAuth.getInstance()
                        .signInWithCredential(FacebookAuthProvider.getCredential(loginResult.accessToken.token))
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                val currentUser = firebaseAuth.currentUser
                                val reference = FirebaseDatabase.getInstance().getReference("users")
                                val user = User(
                                    email = currentUser?.email,
                                    uid = currentUser!!.uid
                                )
                                showProgressDialog()
                                reference.child(firebaseAuth.currentUser!!.uid).setValue(user)
                                    .addOnCompleteListener {
                                        dismissProgressDialog()
                                        if (it.isSuccessful) {
                                            startActivity<AddVitalsDetailsActivity>()
                                            activity!!.finish()
                                        } else {
                                            toast("Error occurred while signing with Facebook. Please try again")
                                        }
                                    }
                                toast("Sign in successful")
                                startActivity<AddVitalsDetailsActivity>()
                                activity?.finish()
                            } else {
                                onError(FacebookException())
                            }
                        }

                }

                override fun onCancel() {
                    dismissProgressDialog()
                    toast("Cancelled")
                }

                override fun onError(error: FacebookException) {
                    dismissProgressDialog()
                    Crashlytics.logException(error)
                    toast("Error occurred while signing with Facebook. Please try again")
                }
            })


    }

    private fun setOnClickListeners() {
        btnSignUpEmail.setOnClickListener {
            startActivity<ManualLoginActivity>("method" to "email")
        }
        btnSignUpPhoneNumber.setOnClickListener {
            startActivity<ManualLoginActivity>("method" to "phone_number")
        }
        btnSignInGoogle.setOnClickListener {
            signInWithGoogle()
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(
            signInIntent,
            RC_SIGN_IN
        )
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            showProgressDialog()
            val googleSignInAccount =
                completedTask.getResult<ApiException>(ApiException::class.java)
            FirebaseAuth.getInstance().signInWithCredential(
                GoogleAuthProvider.getCredential(
                    googleSignInAccount?.idToken,
                    null
                )
            ).addOnCompleteListener {
                dismissProgressDialog()
                if (it.isSuccessful) {
                    val currentUser = firebaseAuth.currentUser
                    toast("Welcome back ${currentUser?.displayName}")
                    val reference = FirebaseDatabase.getInstance().getReference("users")
                    val user = User(
                        email = currentUser?.email,
                        uid = currentUser!!.uid
                    )
                    reference.child(firebaseAuth.currentUser!!.uid).setValue(user)
                        .addOnCompleteListener {
                            startActivity<AddVitalsDetailsActivity>()
                            activity!!.finish()
                        }

                } else {
                    toast("Error occurred while signing in with Google. Please try again.")
                }

            }
        } catch (e: ApiException) {
            dismissProgressDialog()
            e.printStackTrace()
            Crashlytics.logException(e)
            toast("Error occurred while signing in with Google. Please try again.")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
