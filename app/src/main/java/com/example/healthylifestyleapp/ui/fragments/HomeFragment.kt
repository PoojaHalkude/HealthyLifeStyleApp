package com.example.healthylifestyleapp.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.DrinkSettingActivity
import com.example.healthylifestyleapp.ui.activities.FoodSettingActivity
import com.example.healthylifestyleapp.ui.activities.HealthTipsActivity
import com.example.healthylifestyleapp.ui.activities.SleepSettingActivity
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.LLHeaderDrink -> {
                startActivity<DrinkSettingActivity>()

            }
            R.id.LLHeaderSleep -> {
                startActivity<SleepSettingActivity>()
            }
            R.id.LLHeaderFood -> {
                startActivity<FoodSettingActivity>()
            }

            R.id.llHeaderTips -> {
                startActivity<HealthTipsActivity>()
            }
        }
    }

    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LLHeaderDrink.setOnClickListener(this)
        LLHeaderSleep.setOnClickListener(this)
        LLHeaderFood.setOnClickListener(this)
        llHeaderTips.setOnClickListener(this)
    }


}
