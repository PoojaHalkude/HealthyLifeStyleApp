package com.example.healthylifestyleapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatTextView AppCompatTextViewSignUp,AppCompatTextViewForgotPassword,AppCompatTextViewLogin;
    AppCompatButton appCompatButtonSignUpEmail,appCompatButtonSignUpPhone;
    GoogleSignInClient mGoogleSignInClient;
ScrollView MainHeader;
    AppCompatEditText AppCompatEditTextEmail,AppCompatEditTextPassword,AppCompatEditTextUserName;
    private static final int RC_SIGN_IN=9001;

    SignInButton sign_in_button;
    LoginButton loginButton;
    private FirebaseAuth auth;
    Context context=this;
    CallbackManager callbackManager;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        getKeyHash("SHA");
        iniUI();
        initListner();
        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();
        FirebaseApp.initializeApp(context);
        sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setSize(SignInButton.SIZE_STANDARD);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        //Google Integration

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();


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
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                Toast.makeText(getApplicationContext(), "Login success", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(MainActivity.this,StartedActivity.class);
                startActivity(a);
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "Cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"Login Error: +error.getMessage()",Toast.LENGTH_SHORT );

            }
        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
       // LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

        MainHeader=findViewById(R.id.MainHeader);
        MainHeader.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                hideKeyboardView(v);

                return false;
            }
        });
    }

    private void hideKeyboardView(View view) {
        InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    protected void onStart() {
        updateUI(true);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
       /* if (account!=null)
        {
            startActivity(new Intent(this,StartedActivity.class));
        }*/
        super.onStart();

    }
    private void updateUI(boolean isLogin)
    {
        if (isLogin)
        {
            sign_in_button.setVisibility(View.VISIBLE);
        }


    }

    private void initListner() {
        AppCompatTextViewSignUp.setOnClickListener(this);
        AppCompatTextViewForgotPassword.setOnClickListener(this);


    }
    private void getKeyHash(String hashStretagy) {
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo(BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance(hashStretagy);
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("KeyHash" , something);
                // Notification.registerGCM(this);

            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found" , e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm" , e.toString());
        } catch (Exception e) {
            Log.e("exception" , e.toString());
        }
    }
    private void iniUI() {
        AppCompatTextViewSignUp=findViewById(R.id.AppCompatTextViewSignUp);
        appCompatButtonSignUpEmail=findViewById(R.id.appCompatButtonSignUpEmail);
        appCompatButtonSignUpPhone=findViewById(R.id.appCompatButtonSignUpPhone);
        AppCompatEditTextEmail=findViewById(R.id.AppCompatEditTextEmail);
        AppCompatEditTextPassword=findViewById(R.id.AppCompatEditTextPassword);
        AppCompatEditTextUserName=findViewById(R.id.AppCompatEditTextUserName);
        AppCompatTextViewForgotPassword=findViewById(R.id.AppCompatTextViewForgotPassword);
        sign_in_button=findViewById(R.id.sign_in_button);
        sign_in_button.setSize(SignInButton.SIZE_STANDARD);

        AppCompatTextViewLogin=findViewById(R.id.AppCompatTextViewLogin);
    }


    @Override
    public void onClick(View v) {

        Fragment fr = new Fragment() ;
        switch ((v.getId()))
        {
            case R.id.AppCompatTextViewSignUp:
                fr = new FragmentSignUp();
                sign_in_button.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
                //AppCompatTextViewSignUp.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                break;

            case R.id.appCompatButtonSignUpPhone:
                sign_in_button.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
                fr=new FragmentPhone();
                break;
            case R.id.AppCompatTextViewLogin:
                fr=new FragmentLogin();
                sign_in_button.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.VISIBLE);
               // AppCompatTextViewLogin.setBackgroundColor(getResources().getColor(R.color.colorYellow));
                /*sign_in_button.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);*/
                break;
            case R.id.sign_in_button:
                SignInWithGoogle();
                break;
            case R.id.appCompatButtonSignUpEmail:
                sign_in_button.setVisibility(View.GONE);
                loginButton.setVisibility(View.GONE);
                fr=new FragmentEmail();
                break;
            case R.id.AppCompatTextViewForgotPassword:
                finish();
                Intent passIntent= new Intent(this, ForgetPasswordActivity.class);
                startActivity(passIntent);
                break;

            default:
                return;

        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fr);
        fragmentTransaction.commit();

    }

    private void SignInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
            finish();

        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent i1=new Intent(this,StartedActivity.class);
            startActivity(i1);
            // Signed in successfully, show authenticated UI.
            updateUI(true);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more info
            Toast.makeText(this,"failed",Toast.LENGTH_LONG);
            updateUI(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
