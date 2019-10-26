package com.example.healthylifestyleapp.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_profile.*
import kotlinx.android.synthetic.main.content_user_profile.*

class UserProfileActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    View.OnClickListener {
    override fun getRoot(): View {
        return drawer_layout
    }

    private lateinit var demoRef: DatabaseReference
    private lateinit var rootRef: DatabaseReference
    internal var imageViewProfilePicture: ImageView? = null

    internal var TextViewUserName1: TextView? = null
    internal var TextViewEmail1: TextView? = null
    internal var mfirebaseuser: FirebaseUser? = null

    internal var ProfileUserRef: DatabaseReference? = null
    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private val currentUserId: String? = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    internal var context: Context = this
    internal var TAG = "UserProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        initListner()
        //database reference pointing to root of database
        rootRef = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        mAuth.currentUser!!.uid
        currentUser = mAuth.currentUser
        ////code for storing gmail profile data/////
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val account = GoogleSignIn.getLastSignedInAccount(this)

        if (account != null) {
            val personName = account.displayName
            val personGivenName = account.givenName
            val personFamilyName = account.familyName
            val personEmail = account.email
            val personId = account.id
            val personPhoto = account.photoUrl
            //   TextViewUserName1.setText("Name:" + personName);
            // TextViewEmail1.setText("Email:" + personEmail);
            /* Glide.with(this).load(personPhoto).into(imageViewProfilePicture);

            startActivity(new Intent(this, UserProfileActivity.class));*/
            /*   Uri profilePictureUri = ImageRequest.getProfilePictureUri(Profile.getCurrentProfile().getId(), dimensionPixelSize , dimensionPixelSize );
            Glide.with(this).load(profilePictureUri)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(imageViewProfilePicture);*/
            //  updateUI(account);
            Log.e("Pooja", "Value of:" + personEmail!!)

        }


        //database reference pointing to demo node
        demoRef = rootRef.child("All_Image_Uploads_Database")
        demoRef.child("value").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue<String>(String::class.java!!)
                //               TextViewUserName.setText(value);
                //TextViewEmail.setText(value);
                // int imgval= (int) dataSnapshot.getValue();
                //imageView.setImageResource(imgval);


            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        })
        // FloatingActionButton fab = findViewById(R.id.fab);
        /*  fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        val navHeaderView = navigationView.getHeaderView(0)
        updateNavProfile()


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

    private fun initListner() {
        ImageViewHome.setOnClickListener(this)
        ImageViewActivity.setOnClickListener(this)
        ImageViewSettings.setOnClickListener(this)
        LLHeaderDrink.setOnClickListener(this)
        LLHeaderSleep.setOnClickListener(this)
        LLHeaderFood.setOnClickListener(this)
        llHeaderTips.setOnClickListener(this)
    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user_profile, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_home) {
            val homeIntent = Intent(this, ProfileScreenActivity::class.java)
            startActivity(homeIntent)

        } else if (id == R.id.nav_home_recipes) {
            val recipesIntent = Intent(this, HealthyRecipesActivity::class.java)
            startActivity(recipesIntent)

        } else if (id == R.id.nav_about_us) {

        } else if (id == R.id.nav_privacy) {

            val privacyIntent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(privacyIntent)
        } else if (id == R.id.nav_logout) {
            val dialog = Dialog(context)
            dialog.setContentView(R.layout.custom_daiolg_logout)
            dialog.setTitle("Title...")

            // set the custom dialog components - text, image and button
            //TextView text = (TextView) dialog.findViewById(R.id.text);
            //text.setText("Android custom dialog example!");
            val ButtonNo = dialog.findViewById<View>(R.id.ButtonNo) as Button
            val ButtonYes = dialog.findViewById<View>(R.id.ButtonYes) as Button
            // if button is clicked, close the custom dialog
            ButtonNo.setOnClickListener {

                dialog.dismiss()
            }
            ButtonYes.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                val logoutIntent = Intent(this@UserProfileActivity, MainActivity::class.java)
                logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(logoutIntent)
            }
            dialog.show()

            //logOut();

        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    /* private void logOut() {
        Intent logoutIntent = new Intent(this, MainActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(logoutIntent);
    }*/

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ImageViewSettings -> {
                val settingIntent = Intent(this, SettingsActivity::class.java)
                startActivity(settingIntent)
            }
            R.id.ImageViewHome -> {
                val HomeIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(HomeIntent)
            }
            R.id.ImageViewActivity -> {
                val ActivityIntent = Intent(this, DailyAgendaACtivity::class.java)
                startActivity(ActivityIntent)
            }
            R.id.LLHeaderDrink -> {
                val DrinkIntent = Intent(this, DrinkSettingActivity::class.java)
                startActivity(DrinkIntent)
            }
            R.id.LLHeaderSleep -> {
                val SleepIntent = Intent(this, SleepSettingActivity::class.java)
                startActivity(SleepIntent)
            }
            R.id.LLHeaderFood -> {
                val FoodIntent = Intent(this, FoodSettingActivity::class.java)
                startActivity(FoodIntent)
                val Intenttips = Intent(this, HealthTipsActivity::class.java)
                startActivity(Intenttips)
            }

            R.id.llHeaderTips -> {
                val Intenttips = Intent(this, HealthTipsActivity::class.java)
                startActivity(Intenttips)
            }
        }

    }

    /// tries for nav header
    fun updateNavProfile() {
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val nav_userName = headerView.findViewById<TextView>(R.id.nav_userName)
        val nav_userEmail = headerView.findViewById<TextView>(R.id.nav_userEmail)
        val nav_userPhoto = headerView.findViewById<ImageView>(R.id.nav_userPhoto)

        Log.d(TAG, "updateNavProfile: Email " + mAuth.uid!!)
        nav_userEmail.text = currentUser!!.email
        nav_userName.text = currentUser!!.displayName
        Glide.with(this).load(currentUser!!.photoUrl).into(nav_userPhoto)

        //        startActivity(new Intent(this, UserProfileActivity.class));

    }
}
