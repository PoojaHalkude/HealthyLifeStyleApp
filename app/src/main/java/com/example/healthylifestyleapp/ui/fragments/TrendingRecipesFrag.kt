package com.example.healthylifestyleapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthylifestyleapp.ui.adapters.MyCustomAdapter
import com.example.healthylifestyleapp.R
import kotlinx.android.synthetic.main.fragment_list_recipes.*
import java.util.*

class TrendingRecipesFrag : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_recipes, container, false)

        val stringList = ArrayList<String>()

        stringList.add("Grilled Steak")
        stringList.add("Recipe One")
        stringList.add("Recipe Two")
        stringList.add("Recipe Three")
        stringList.add("Recipe Four")
        /*  stringList.add("Item 1F");
        stringList.add("Item 1G");
        stringList.add("Item 1H");
        stringList.add("Item 1I");
        stringList.add("Item 1J");
        stringList.add("Item 1K");
        stringList.add("Item 1L");
        stringList.add("Item 1M");
        stringList.add("Item 1N");
        stringList.add("Item 1O");
        stringList.add("Item 1P");
        stringList.add("Item 1Q");
        */

        val adapter =
            MyCustomAdapter(stringList, activity!!)
        // MyCustomAdapter adapter= new MyCustomAdapter(stringList,recipes_images);
        //     MyCustomAdapter adapter = new MyCustomAdapter(stringList,recipes_images,getActivity());

        list.adapter = adapter

        return view
    }

    companion object {
        var recipes_images = intArrayOf(
            R.drawable.activity_image,
            R.drawable.grilled_steak,
            R.drawable.ic_account_circle,
            R.drawable.ic_email_black,
            R.mipmap.ic_launcher
        )
    }
}
