package com.example.healthylifestyleapp.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.ui.fragments.ActivitiesFragment
import com.example.healthylifestyleapp.ui.fragments.HomeFragment
import com.example.healthylifestyleapp.ui.fragments.RecipesFragment
import com.example.healthylifestyleapp.ui.fragments.SettingsFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_user_profile.*

class UserProfileActivity : BaseActivity() {
    override fun getRoot(): View? {
        return drawer_layout
    }

    private lateinit var demoRef: DatabaseReference
    private lateinit var rootRef: DatabaseReference


    private lateinit var mAuth: FirebaseAuth
    private var currentUser: FirebaseUser? = null
    private val currentUserId: String? = null

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    internal var context: Context = this

    companion object {
        private const val TAG = "UserProfileActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.menu_home)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
/*        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
//        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
        val navHeaderView = navigationView.getHeaderView(0)*/
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
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    val homeIntent = Intent(this, ProfileScreenActivity::class.java)
                    startActivity(homeIntent)

                }
                R.id.menu_activity -> {
                    supportActionBar?.title = getString(R.string.activity)
                    loadFragment(ActivitiesFragment())
                }
                R.id.menu_home -> {
                    supportActionBar?.title = getString(R.string.menu_home)
                    loadFragment(HomeFragment())
                }
                R.id.menu_recipes -> {
                    supportActionBar?.title = getString(R.string.menu_recipes)
                    loadFragment(RecipesFragment())
                }
                R.id.menu_settings -> {
                    supportActionBar?.title = getString(R.string.action_settings)
                    loadFragment(SettingsFragment())
                }
                R.id.nav_home_recipes -> {
                    val recipesIntent = Intent(this, HealthyRecipesActivity::class.java)
                    startActivity(recipesIntent)

                }

                R.id.nav_privacy -> {

                    val privacyIntent = Intent(this, PrivacyPolicyActivity::class.java)
                    startActivity(privacyIntent)
                }
                R.id.nav_logout -> {
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
                        val logoutIntent =
                            Intent(this@UserProfileActivity, OnBoardingOptionsActivity::class.java)
                        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(logoutIntent)
                    }
                    dialog.show()

                    //logOut();

                }
            }
            return@setOnNavigationItemSelectedListener true
        }
        bottomNavigationView.selectedItemId = R.id.menu_home
    }


    override fun onBackPressed() {
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


    private fun loadFragment(fr: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fr, fr.tag).commit()
    }

    /* private void logOut() {
        Intent logoutIntent = new Intent(this, OnBoardingOptionsActivity.class);
        logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(logoutIntent);
    }*/

    /// tries for nav header
    fun updateNavProfile() {
/*        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val nav_userName = headerView.findViewById<TextView>(R.id.nav_userName)
        val nav_userEmail = headerView.findViewById<TextView>(R.id.nav_userEmail)
        val nav_userPhoto = headerView.findViewById<ImageView>(R.id.nav_userPhoto)

        Log.d(TAG, "updateNavProfile: Email " + mAuth.uid!!)
        nav_userEmail.text = currentUser!!.email
        nav_userName.text = currentUser!!.displayName
        Glide.with(this).load(currentUser!!.photoUrl).into(nav_userPhoto)*/

        //        startActivity(new Intent(this, UserProfileActivity.class));

    }
}
