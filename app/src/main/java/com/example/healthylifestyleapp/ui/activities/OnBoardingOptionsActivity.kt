package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.activity.BaseActivity
import com.example.healthylifestyleapp.ui.fragments.FragmentLogin
import com.example.healthylifestyleapp.ui.fragments.FragmentSignUp
import com.example.healthylifestyleapp.utils.hideSoftKeyboard
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_on_boarding_options.*


class OnBoardingOptionsActivity : BaseActivity() {
    override fun getRoot(): View? {
        return MainHeader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_options)
        initialize()
    }

    private fun initialize() {
        initListener()
    }

    private fun initListener() {
        MainHeader.setOnTouchListener { v, _ ->
            v.hideSoftKeyboard()
            false
        }
        loginOptionsTabLayout.addOnTabSelectedListener(object :
            TabLayout.BaseOnTabSelectedListener<TabLayout.Tab> {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                when (p0?.text) {
                    "Sign Up" -> {
                        loginOptionsTabLayout.hideSoftKeyboard()

                    }
                    "Sign In" -> {
                        loginOptionsTabLayout.hideSoftKeyboard()
                    }
                }
            }

        })
        viewPager.adapter = OnBoardingPagerAdapter(supportFragmentManager)
        loginOptionsTabLayout.setupWithViewPager(viewPager)
    }

/*    private fun getKeyHash(hashStrategy: String) {
        val info: PackageInfo
        try {
            info = packageManager.getPackageInfo(
                BuildConfig.APPLICATION_ID,
                PackageManager.GET_SIGNATURES
            )
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

    }*/


    class OnBoardingPagerAdapter(fm: FragmentManager) :
        FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        private var fr = Fragment()

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    fr = FragmentSignUp()
                }
                1 -> {
                    fr = FragmentLogin()
                }
            }
            return fr
        }

        override fun getCount(): Int {
            return 2
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> {
                    "Sign Up"
                }
                1 -> {
                    "Sign In"
                }
                else -> {
                    ""
                }
            }
        }
    }
}
