package com.example.healthylifestyleapp.ui.fragments


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.UserUploadInfo
import com.example.healthylifestyleapp.ui.activities.FeedbackFormActivity
import com.example.healthylifestyleapp.ui.activities.HealthDataSettingsActivity
import com.example.healthylifestyleapp.ui.activities.LanguageSettingActivity
import com.example.healthylifestyleapp.ui.activities.UpdateProfileDataActivity
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.utils.logout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.nav_header_user_profile.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment(), View.OnClickListener {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initLisetner()
        showProfile()
    }

    private fun showProfile() {
//        nav_userEmail.text = firebaseAuth.currentUser!!.email
//        nav_userName.text = firebaseAuth.currentUser!!.displayName
//        Glide.with(context).load(firebaseAuth.currentUser!!.photoUrl).into(nav_userPhoto)
        fetchProfileDetails()
    }

    private fun fetchProfileDetails() {
        val user = FirebaseAuth.getInstance().currentUser
        val name =
            if (user != null && !TextUtils.isEmpty(user.displayName)) user.displayName else ""
        val email = if (user != null && !TextUtils.isEmpty(user.email)) user.email else ""
        val phoneNumber =
            if (user != null && !TextUtils.isEmpty(user.phoneNumber)) user.phoneNumber else ""
        val photoUrl =
            if (user != null && !TextUtils.isEmpty(user.photoUrl.toString())) user.photoUrl.toString() else ""
        firebaseDatabase.getReference("users/${firebaseAuth.currentUser!!.uid}")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    val profile = p0.getValue(UserUploadInfo::class.java)
                    if (profile != null) {
                        if (!TextUtils.isEmpty(profile.userName)) {
                            nav_userName.text = profile.userName
                        } else {
                            nav_userName.text = name
                        }

                        if (!TextUtils.isEmpty(profile.myemail)) {
                            nav_userEmail.text = profile.myemail
                        } else {
                            nav_userEmail.text = email
                        }

                        if (!TextUtils.isEmpty(profile.mobile)) {
                            nav_userPhoneNumber.text = profile.mobile
                        } else {
                            nav_userPhoneNumber.text = phoneNumber
                        }

                        if (!TextUtils.isEmpty(photoUrl) && photoUrl != "null") {
                            Picasso.get().load(photoUrl).placeholder(R.drawable.ic_account_circle)
                                .into(nav_userPhoto)
                        } else {
                            Picasso.get().load(profile.imageURL)
                                .placeholder(R.drawable.ic_account_circle)
                                .into(nav_userPhoto)
                        }
                    } else {

                    }
                }
            })
    }

    private fun initLisetner() {
        rlHeaderProfile.setOnClickListener(this)
        rlHeaderLogOut.setOnClickListener(this)
        rlHeaderHealthData.setOnClickListener(this)
        rlHeaderLanguage.setOnClickListener(this)
        rlHeaderReferFriend.setOnClickListener(this)
        rlHeaderFeedback.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlHeaderLogOut -> {
                // custom dialog
                val dialog = Dialog(context!!)
                dialog.setContentView(R.layout.custom_daiolg_logout)
                dialog.setTitle("Title...")

                // set the custom dialog components - text, image and button
                //TextView text = (TextView) dialog.findViewById(R.id.text);
                //text.setText("Android custom dialog example!");
                val noButton = dialog.findViewById<View>(R.id.ButtonNo) as Button
                val buttonYes = dialog.findViewById<View>(R.id.ButtonYes) as Button
                // if button is clicked, close the custom dialog
                noButton.setOnClickListener {
                    dialog.dismiss()
                }
                buttonYes.setOnClickListener {
                    (activity as BaseActivity).logout()
                }
                dialog.show()
            }

            R.id.rlHeaderLanguage -> {
                startActivity<LanguageSettingActivity>()
            }
            R.id.rlHeaderHealthData -> {
                startActivity<HealthDataSettingsActivity>()
            }
            R.id.rlHeaderProfile -> {
                startActivity<UpdateProfileDataActivity>()
            }
            R.id.rlHeaderReferFriend -> {
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.type = "text/plain"
                val shareBody =
                    String.format(
                        getString(R.string.share_link),
                        context?.resources?.getString(R.string.app_name)
                    )
                myIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                startActivity(Intent.createChooser(myIntent, getString(R.string.menu_share)))
            }
            R.id.rlHeaderFeedback -> {
                startActivity<FeedbackFormActivity>()
            }
        }

    }


}
