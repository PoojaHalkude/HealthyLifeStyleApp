package com.example.healthylifestyleapp.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.healthylifestyleapp.ui.fragments.AllRecipesFrag
import com.example.healthylifestyleapp.ui.fragments.NewRecipesFrag
import com.example.healthylifestyleapp.ui.fragments.TrendingRecipesFrag

internal class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = AllRecipesFrag()
        } else if (position == 1) {
            fragment = TrendingRecipesFrag()
        } else if (position == 2) {
            fragment = NewRecipesFrag()
        }
        return fragment!!
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "ALL"
        } else if (position == 1) {
            title = "TRENDING"
        } else if (position == 2) {
            title = "NEW"
        }
        return title
    }

}
