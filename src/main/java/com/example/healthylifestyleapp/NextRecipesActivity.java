package com.example.healthylifestyleapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NextRecipesActivity extends AppCompatActivity implements View.OnClickListener {
    Button ButtonInstructions, ButtonIngrediants;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_recipes);
        ButtonIngrediants = findViewById(R.id.ButtonIngrediants);
        ButtonInstructions = findViewById(R.id.ButtonInstructions);
        ButtonInstructions.setOnClickListener(this);
        ButtonIngrediants.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        android.app.Fragment fragment;
        switch ((v.getId()))
        {
            case R.id.ButtonInstructions:
                fragment = new FragmentInstructions();
                break;
            case R.id.ButtonIngrediants:
                fragment=new FragmentIngrediants();
                break;
            default:
                return;

        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_place, fragment);
        fragmentTransaction.commit();
    }
}




