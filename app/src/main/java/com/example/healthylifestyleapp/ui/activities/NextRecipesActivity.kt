package com.example.healthylifestyleapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.fragments.FragmentIngrediants
import com.example.healthylifestyleapp.ui.fragments.FragmentInstructions
import kotlinx.android.synthetic.main.activity_next_recipes.*

class NextRecipesActivity : AppCompatActivity(), View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next_recipes)

        ButtonInstructions.setOnClickListener(this)
        ButtonIngrediants.setOnClickListener(this)
    }

    override fun onClick(v: View) {

        val fragment: Fragment = when (v.id) {
            R.id.ButtonInstructions -> FragmentInstructions()
            R.id.ButtonIngrediants -> FragmentIngrediants()
            else -> return
        }
        val fm = supportFragmentManager
        val fragmentTransaction = fm.beginTransaction()
        fragmentTransaction.replace(R.id.FragmentForInstructions, fragment)
        fragmentTransaction.commit()
    }
}




