package com.example.healthylifestyleapp.ui.fragments


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.FeedbackFormActivity
import com.example.healthylifestyleapp.ui.activities.HealthDataSettingsActivity
import com.example.healthylifestyleapp.ui.activities.LanguageSettingActivity
import com.example.healthylifestyleapp.ui.activities.ProfileScreenActivity
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import com.example.healthylifestyleapp.utils.logout
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
        nav_userEmail.text = firebaseAuth.currentUser!!.email
        nav_userName.text = firebaseAuth.currentUser!!.displayName
        Glide.with(context).load(firebaseAuth.currentUser!!.photoUrl).into(nav_userPhoto)
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
                startActivity<ProfileScreenActivity>()
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
