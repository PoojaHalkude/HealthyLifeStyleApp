package com.example.healthylifestyleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.ui.activities.base.fragment.BaseFragment
import kotlinx.android.synthetic.main.activity_healthy_recipes.*

class RecipesFragment : BaseFragment() {
    override fun getRoot(): View? {
        return rootView
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_healthy_recipes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initialize()
    }

    private fun initialize() {

    }
}