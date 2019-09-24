package com.example.healthylifestyleapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TrendingRecipesFrag extends Fragment {
    ListView list;
    ImageView imageView;
    public static int [] recipes_images={R.drawable.activity_image,
            R.drawable.grilled_steak,
            R.drawable.ic_account_circle,
            R.drawable.ic_email_black,
            R.mipmap.ic_launcher};

    public TrendingRecipesFrag() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_recipes, container, false);
        imageView=view.findViewById(R.id.ImageviewRecipesList);

        list = (ListView) view.findViewById(R.id.list);
        ArrayList stringList= new ArrayList();

        stringList.add("Grilled Steak");
        stringList.add("Recipe One");
        stringList.add("Recipe Two");
        stringList.add("Recipe Three");
        stringList.add("Recipe Four");
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

        MyCustomAdapter adapter = new MyCustomAdapter(stringList,getActivity());
        // MyCustomAdapter adapter= new MyCustomAdapter(stringList,recipes_images);
        //     MyCustomAdapter adapter = new MyCustomAdapter(stringList,recipes_images,getActivity());

        list.setAdapter(adapter);

        return view;
    }
}
