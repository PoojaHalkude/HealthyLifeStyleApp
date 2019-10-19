package com.example.healthylifestyleapp.ui.activities

import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract.Intents.Insert.EMAIL
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.*
import com.example.healthylifestyleapp.ui.fragments.FragmentEmail
import com.example.healthylifestyleapp.ui.fragments.FragmentLogin
import com.example.healthylifestyleapp.ui.fragments.FragmentPhone
import com.example.healthylifestyleapp.ui.fragments.FragmentSignUp
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
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MainActivity : AppCompatActivity(), View.OnClickListener {


    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null
    internal var context: Context = this
    private lateinit var callbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FacebookSdk.sdkInitialize(applicationContext)
        setContentView(R.layout.activity_main)
        getKeyHash("SHA")
        iniUI()
        initListner()
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance()
        FirebaseApp.initializeApp(context)
        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
        findViewById<View>(R.id.sign_in_button).setOnClickListener(this)
        //Google Integration

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        login_button.setReadPermissions(listOf(EMAIL))
        callbackManager = CallbackManager.Factory.create()


        /*loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(MainActivity.this,UserProfileActivity.class);
                startActivity(a);
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(getApplicationContext(),"Login Error: +error.getMessage()",Toast.LENGTH_SHORT );

            }
        });*/

        //updateNavProfile();
        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {

                Toast.makeText(applicationContext, "Login success", Toast.LENGTH_SHORT).show()
                val a = Intent(this@MainActivity, StartedActivity::class.java)
                startActivity(a)
            }

            override fun onCancel() {
                Toast.makeText(applicationContext, "Cancel", Toast.LENGTH_SHORT).show()

            }

            override fun onError(error: FacebookException) {
                Toast.makeText(applicationContext, "Login Error: +error.getMessage()", Toast.LENGTH_SHORT).show()

            }
        })


        MainHeader.setOnTouchListener { v, _ ->
            hideKeyboardView(v)

            false
        }
    }

    private fun hideKeyboardView(view: View) {
        val `in` = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        `in`.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

    }

    override fun onStart() {
        updateUI(true)
        val account = GoogleSignIn.getLastSignedInAccount(this)
        /* if (account!=null)
        {
            startActivity(new Intent(this,StartedActivity.class));
        }*/
        super.onStart()

    }

    private fun updateUI(isLogin: Boolean) {
        if (isLogin) {
            sign_in_button.visibility = View.VISIBLE
        }


    }

    private fun initListner() {
        AppCompatTextViewSignUp.setOnClickListener(this)
        AppCompatTextViewForgotPassword.setOnClickListener(this)


    }

    private fun getKeyHash(hashStrategy: String) {
        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance(hashStrategy)
                md.update(signature.toByteArray())
                val something = String(Base64.encode(md.digest(), 0))
                Log.e("KeyHash", something)
                // Notification.registerGCM(this);

            }
        } catch (e1: PackageManager.NameNotFoundException) {
            Log.e("name not found", e1.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.e("no such an algorithm", e.toString())
        } catch (e: Exception) {
            Log.e("exception", e.toString())
        }

    }

    private fun iniUI() {
        sign_in_button.setSize(SignInButton.SIZE_STANDARD)
    }


    override fun onClick(v: View) {

        var fr = androidx.fragment.app.Fragment()
        when (v.id) {
            R.id.AppCompatTextViewSignUp -> {
                fr = FragmentSignUp()
                sign_in_button.visibility = View.VISIBLE
                login_button.visibility = View.VISIBLE
            }

            R.id.appCompatButtonSignUpPhone -> {
                sign_in_button.visibility = View.GONE
                login_button.visibility = View.GONE
                fr = FragmentPhone()
            }
            R.id.AppCompatTextViewLogin -> {
                fr = FragmentLogin()
                sign_in_button.visibility = View.VISIBLE
                login_button.visibility = View.VISIBLE
            }
            R.id.sign_in_button -> signInWithGoogle()
            R.id.appCompatButtonSignUpEmail -> {
                sign_in_button.visibility = View.GONE
                login_button.visibility = View.GONE
                fr = FragmentEmail()
            }
            R.id.AppCompatTextViewForgotPassword -> {
                finish()
                val passIntent = Intent(this, ForgetPasswordActivity::class.java)
                startActivity(passIntent)
            }

            else -> return
        }//AppCompatTextViewSignUp.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        // AppCompatTextViewLogin.setBackgroundColor(getResources().getColor(R.color.colorYellow));
        /*sign_in_button.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);*/
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.fragment_place, fr)
        fragmentTransaction.commit()

    }

    private fun signInWithGoogle() {
        val signInIntent = mGoogleSignInClient?.signInIntent
        startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            finish()

        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult<ApiException>(ApiException::class.java!!)
            val i1 = Intent(this, StartedActivity::class.java)
            startActivity(i1)
            // Signed in successfully, show authenticated UI.
            updateUI(true)
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more info
            Toast.makeText(this, "failed", Toast.LENGTH_LONG)
            updateUI(false)
        }

    }

    companion object {
        private const val RC_SIGN_IN = 9001
    }

    /*public void updateNavProfile()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView= navigationView.getHeaderView(0);
        TextView nav_userName=headerView.findViewById(R.id.nav_userName);
        TextView nav_userEmail=headerView.findViewById(R.id.nav_userEmail);
        ImageView nav_userPhoto=headerView.findViewById(R.id.nav_userPhoto);

        Log.d("Pooja", "updateNavProfile: Email " +mAuth.getUid());
        nav_userEmail.setText(currentUser.getEmail());
        nav_userName.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(nav_userPhoto);

//        startActivity(new Intent(this, UserProfileActivity.class));

    }*/
}
