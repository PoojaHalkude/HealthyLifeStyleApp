package com.example.healthylifestyleapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfileActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ImageView imageView, ImageViewHome, ImageViewActivity, ImageViewSettings, imageViewProfilePicture;
    TextView TextViewUserName, TextViewEmail, TextViewUserName1, TextViewEmail1;
    FirebaseUser mfirebaseuser;
    DatabaseReference rootRef, demoRef, ProfileUserRef;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private String currentUserId;
    LinearLayout LLHeaderDrink, LLHeaderSleep, LLHeaderFood, llHeaderTips;
    GoogleSignInClient mGoogleSignInClient;

    Context context = this;
    String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        InitUi();
        initListner();
        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mAuth.getCurrentUser().getUid();
        currentUser= mAuth.getCurrentUser();
        ////code for storing gmail profile data/////
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();
         //   TextViewUserName1.setText("Name:" + personName);
           // TextViewEmail1.setText("Email:" + personEmail);
           /* Glide.with(this).load(personPhoto).into(imageViewProfilePicture);

            startActivity(new Intent(this, UserProfileActivity.class));*/
         /*   Uri profilePictureUri = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), dimensionPixelSize , dimensionPixelSize );
            Glide.with(this).load(profilePictureUri)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageViewProfilePicture);*/
            //  updateUI(account);
            Log.e("Pooja", "Value of:" + personEmail);

        }


        //database reference pointing to demo node
        demoRef = rootRef.child("All_Image_Uploads_Database");
        demoRef.child("value").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
//               TextViewUserName.setText(value);
                //TextViewEmail.setText(value);
                // int imgval= (int) dataSnapshot.getValue();
                //imageView.setImageResource(imgval);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        // FloatingActionButton fab = findViewById(R.id.fab);
      /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        updateNavProfile();



        imageView = (ImageView) findViewById(R.id.imageView);
        TextViewUserName = (TextView) findViewById(R.id.TextViewUserName);
        TextViewEmail = (TextView)findViewById(R.id.TextViewEmail);

        //Code for setting profile data to navigation drawer
      /*  FirebaseDatabase.getInstance().getReference((Constatnts.USER_KEy)).child(mfirebaseuser.getEmail().replace(".",","))
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue()!=null)
                {
                    UserUploadInfo userUploadInfo=dataSnapshot.getValue(UserUploadInfo.class);
                    Glide.with(UserProfileActivity.this).load(userUploadInfo.getImageURL()).load(imageView);
                    TextViewUserName.setText(userUploadInfo.getUserName());
                    TextViewEmail.setText(userUploadInfo.getEmail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });*/

    }

    private void initListner() {
        ImageViewHome.setOnClickListener(this);
        ImageViewActivity.setOnClickListener(this);
        ImageViewSettings.setOnClickListener(this);
        LLHeaderDrink.setOnClickListener(this);
        LLHeaderSleep.setOnClickListener(this);
        LLHeaderFood.setOnClickListener(this);
        llHeaderTips.setOnClickListener(this);
    }

    private void InitUi() {
        imageView = findViewById(R.id.imageView);
        TextViewUserName = findViewById(R.id.TextViewUserName);
        TextViewEmail = findViewById(R.id.TextViewEmail);
        ImageViewHome = findViewById(R.id.ImageViewHome);
        ImageViewActivity = findViewById(R.id.ImageViewActivity);
        ImageViewSettings = findViewById(R.id.ImageViewSettings);
        LLHeaderDrink = findViewById(R.id.LLHeaderDrink);
        LLHeaderFood = findViewById(R.id.LLHeaderFood);
        LLHeaderSleep = findViewById(R.id.LLHeaderSleep);
     /*   imageViewProfilePicture = findViewById(R.id.imageViewProfilePicture);
        TextViewUserName1 = findViewById(R.id.TextViewUserName1);
        TextViewEmail1 = findViewById(R.id.TextViewEmail1);*/

        llHeaderTips = findViewById(R.id.llHeaderTips);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent homeIntent = new Intent(this, ProfileScreenActivity.class);
            startActivity(homeIntent);

        } else if (id == R.id.nav_home_recipes) {
            Intent recipesIntent = new Intent(this, HealthyRecipesActivity.class);
            startActivity(recipesIntent);

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_privacy) {

            Intent privacyIntent = new Intent(this, PrivacyPolicyActivity.class);
            startActivity(privacyIntent);
        } else if (id == R.id.nav_logout) {
            final Dialog dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_daiolg_logout);
            dialog.setTitle("Title...");

            // set the custom dialog components - text, image and button
            //TextView text = (TextView) dialog.findViewById(R.id.text);
            //text.setText("Android custom dialog example!");
            Button ButtonNo = (Button) dialog.findViewById(R.id.ButtonNo);
            Button ButtonYes = (Button) dialog.findViewById(R.id.ButtonYes);
            // if button is clicked, close the custom dialog
            ButtonNo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logoutIntent = new Intent(UserProfileActivity.this, MainActivity.class);

                    dialog.dismiss();
                }
            });
            ButtonYes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent logoutIntent = new Intent(UserProfileActivity.this, MainActivity.class);
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(logoutIntent);
                }
            });
            dialog.show();

            ;

            //logOut();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

   /* private void logOut() {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(logoutIntent);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ImageViewSettings:
                Intent settingIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingIntent);
                break;
            case R.id.ImageViewHome:
                Intent HomeIntent = new Intent(this, UserProfileActivity.class);
                startActivity(HomeIntent);
                break;
            case R.id.ImageViewActivity:
                Intent ActivityIntent = new Intent(this, DailyAgendaACtivity.class);
                startActivity(ActivityIntent);
                break;
            case R.id.LLHeaderDrink:
                Intent DrinkIntent = new Intent(this, DrinkSettingActivity.class);
                startActivity(DrinkIntent);
                break;
            case R.id.LLHeaderSleep:
                Intent SleepIntent = new Intent(this, SleepSettingActivity.class);
                startActivity(SleepIntent);
                break;
            case R.id.LLHeaderFood:
                Intent FoodIntent = new Intent(this, FoodSettingActivity.class);
                startActivity(FoodIntent);

            case R.id.llHeaderTips:
                Intent Intenttips = new Intent(this, HealthTipsActivity.class);
                startActivity(Intenttips);


        }

    }
    /// tries for nav header
    public void updateNavProfile()
    {
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView= navigationView.getHeaderView(0);
        TextView nav_userName=headerView.findViewById(R.id.nav_userName);
        TextView nav_userEmail=headerView.findViewById(R.id.nav_userEmail);
        ImageView nav_userPhoto=headerView.findViewById(R.id.nav_userPhoto);

        Log.d(TAG, "updateNavProfile: Email " +mAuth.getUid());
        nav_userEmail.setText(currentUser.getEmail());
        nav_userName.setText(currentUser.getDisplayName());
        Glide.with(this).load(currentUser.getPhotoUrl()).into(nav_userPhoto);

//        startActivity(new Intent(this, UserProfileActivity.class));

    }
}
