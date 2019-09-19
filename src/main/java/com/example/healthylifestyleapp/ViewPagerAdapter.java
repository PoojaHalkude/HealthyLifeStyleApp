package com.example.healthylifestyleapp;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new AllRecipesFrag();
        }
        else if (position == 1)
        {
            fragment = new TrendingRecipesFrag();
        }
        else if (position == 2)
        {
            fragment = new NewRecipesFrag();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if (position == 0)
        {
            title = "ALL";
        }
        else if (position == 1)
        {
            title = "TRENDING";
        }
        else if (position == 2)
        {
            title = "NEW";
        }
        return title;
    }

}
