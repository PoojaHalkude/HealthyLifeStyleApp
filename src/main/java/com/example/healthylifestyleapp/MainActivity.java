package com.example.healthylifestyleapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

/*import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;*/

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatTextView AppCompatTextViewSignUp,AppCompatTextViewForgotPassword;
    AppCompatButton appCompatButtonSignUpEmail,appCompatButtonSignUpPhone;
    GoogleSignInClient mGoogleSignInClient;
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
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getApplicationContext(), "login success", Toast.LENGTH_SHORT).show();
                Intent a = new Intent(MainActivity.this,UserProfileActivity.class);
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




    }
    @Override
    protected void onStart() {

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        super.onStart();
        updateUI(true);
    }
    private void updateUI(boolean isLogin)
    {
        if (isLogin)
        {
            sign_in_button.setVisibility(View.VISIBLE);
        }


    }
      /*  try {

            String strData = "Name : " + account.getDisplayName()
                    + "\r\nEmail : " + account.getEmail() + "\r\nGiven name : " + account.getGivenName()
                    + "\r\nDisplay Name : " + account.getDisplayName() + "\r\nId : "
                    + account.getId();
//+ "\r\nImage URL : " + account.getPhotoUrl().toString();
//+ "\r\nAccount : " + account.getAccount().toString()
           // lblInfo.setText(strData);

            Log.d("Image URL : ", account.getPhotoUrl().toString());
            Log.d("Login Data : ", strData);
            new DownloadImageTask((ImageView) findViewById(R.id.imgProfilePic))
                    .execute(account.getPhotoUrl().toString());

         //   lblHeader.setText("Sign In with Google Successful");
            //btnLogin.setVisibility(View.GONE);
           // btnLogout.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex) {
            Log.w("Pooja", "NullPointerException" + ex.getMessage());

            //  lblInfo.setText(lblInfo.getText().toString() + "\r\n" + "NullPointerException : " + ex.getMessage().toString());
        } catch (RuntimeException ex) {
            Log.w("Pooja", "RuntimeException" +  ex.getMessage());

            // lblInfo.setText(lblInfo.getText().toString() + "\r\n" + "RuntimeException : " + ex.getMessage().toString());
        } catch (Exception ex) {
// lblInfo.setText(ex.getMessage().toString());*/





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
        //   loginButton = (LoginButton) findViewById(R.id.login_button);


    }


    @Override
    public void onClick(View v) {

        Fragment fr = new Fragment() ;
        switch ((v.getId()))
        {
            case R.id.AppCompatTextViewSignUp:
                fr = new FragmentSignUp();
                break;

            case R.id.appCompatButtonSignUpPhone:
                fr=new FragmentPhone();
                break;
            case R.id.AppCompatTextViewLogin:
                fr=new FragmentLogin();
                break;
            case R.id.sign_in_button:
                SignInWithGoogle();
                break;
            case R.id.appCompatButtonSignUpEmail:
                fr=new FragmentEmail();
                break;
            case R.id.AppCompatTextViewForgotPassword:
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
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            Intent i1=new Intent(this,UserProfileActivity.class);
            startActivity(i1);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(true);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Pooja", "signInResult:failed code=" + e.getStatusCode());
            updateUI(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
