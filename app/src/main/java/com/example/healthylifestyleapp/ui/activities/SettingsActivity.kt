package com.example.healthylifestyleapp.ui.activities

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity(), View.OnClickListener {

    internal var context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        initListner()
    }

    private fun initListner() {
        rlHeaderProfile.setOnClickListener(this)
        ImageViewSettings.setOnClickListener(this)
        ImageViewHome.setOnClickListener(this)
        ImageViewActivity.setOnClickListener(this)
        rlHeaderLogOut.setOnClickListener(this)
        ImageViewMenuSetting.setOnClickListener(this)
        rlHeaderHealthData.setOnClickListener(this)
        rlHeaderLanguage.setOnClickListener(this)
        rlHeaderReferFriend.setOnClickListener(this)
        rlHeaderFeedback.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.rlHeaderLogOut -> {
                // custom dialog
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
                    val logoutIntent = Intent(this@SettingsActivity, MainActivity::class.java)

                    dialog.dismiss()
                }
                ButtonYes.setOnClickListener {
                    val logoutIntent = Intent(this@SettingsActivity, MainActivity::class.java)
                    logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    startActivity(logoutIntent)
                }
                dialog.show()
            }
            R.id.ImageViewSettings -> {
                val settingIntent = Intent(this, UserProfileActivity::class.java)
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

            R.id.rlHeaderLanguage -> {
                val langIntent = Intent(this, LanguageSettingActivity::class.java)
                startActivity(langIntent)
            }

            R.id.rlHeaderHealthData -> {
                val HealthDataIntent = Intent(this, HealthDataSettingsActivity::class.java)
                startActivity(HealthDataIntent)
            }
            R.id.rlHeaderProfile -> {
                val ProfileIntent = Intent(this, ProfileScreenActivity::class.java)
                startActivity(ProfileIntent)
            }
            R.id.rlHeaderReferFriend -> {
                /* Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);*/
                val myIntent = Intent(Intent.ACTION_SEND)
                myIntent.type = "text/plain"
                val shareBody = "This is my text to send."
                val shareSub = "Your subject/Link"
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody)
                myIntent.putExtra(Intent.EXTRA_TEXT, shareSub)
                startActivity(Intent.createChooser(myIntent, "Share Link using"))
            }
            R.id.ImageViewMenuSetting -> {
                val MenuIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(MenuIntent)
            }
            R.id.rlHeaderFeedback -> {
                val feedbackIntent = Intent(this, FeedbackFormActivity::class.java)
                startActivity(feedbackIntent)
            }
        }

    }
}
