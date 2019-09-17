package com.example.healthylifestyleapp;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class RecipesActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    private ViewPager viewPager;
    ImageView imgBackSpace;
    private ListView listViewRecipes;
    RecipesAdapter list_adapter;
    String[] recipes = new String[] { "recipes1",
            "recipes2",
            "recipes3",
            "recipes4",

    };

    public static int [] recipes_images={R.drawable.activity_image,
            R.drawable.grilled_steak,
            R.drawable.ic_account_circle,
            R.drawable.ic_email_black,
            R.mipmap.ic_launcher};
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_recipes);
       /* imgBackSpace.findViewById(R.id.imgBackSpace);
        imgBackSpace.setOnClickListener(this);*/
        tabLayout=findViewById(R.id.tabs);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager=findViewById(R.id.MyViewpager);
        tabLayout.setupWithViewPager(viewPager);
        listViewRecipes=findViewById(R.id.listViewRecipes);

        setupViewPager(viewPager);
        list_adapter = new RecipesAdapter(this,recipes, recipes_images);
        listViewRecipes.setAdapter(list_adapter);

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new AllRecipes(), "All");
        adapter.addFragment(new TrendingRecipes(), "Trending");
        adapter.addFragment(new NewRecipes(), "New");
        viewPager.setAdapter(adapter);
    }

  /*  @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.imgBackSpace:
                Intent backIntent= new Intent(this,UserProfileActivity.class);
                startActivity(backIntent);

        }
    }*/

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
