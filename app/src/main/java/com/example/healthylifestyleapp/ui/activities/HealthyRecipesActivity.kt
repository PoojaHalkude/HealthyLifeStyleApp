package com.example.healthylifestyleapp.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.adapters.ViewPagerAdapter

import kotlinx.android.synthetic.main.activity_healthy_recipes.*

class HealthyRecipesActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_healthy_recipes)
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //        setSupportActionBar(toolbar);

        /*   FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        val viewPagerAdapter =
            ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
        imgBackSpaceRecipes.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imgBackSpaceRecipes -> {
                val recipesIntent = Intent(this, UserProfileActivity::class.java)
                startActivity(recipesIntent)
            }
        }

    }
}
