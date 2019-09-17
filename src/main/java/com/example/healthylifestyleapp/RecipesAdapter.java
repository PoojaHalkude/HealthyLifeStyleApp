package com.example.healthylifestyleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

class RecipesAdapter extends BaseAdapter {
        String [] result;
        Context context;
        int [] imageId;
        private static LayoutInflater inflater=null;

   /* public RecipesAdapter(MainActivity mainActivity, String[] prgmNameList, int[] prgmImages) {
// TODO Auto-generated constructor stub
            result=prgmNameList;
            context=mainActivity;
            imageId=prgmImages;
            inflater = ( LayoutInflater )context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }*/

        @Override
        public int getCount() {

// TODO Auto-generated method stub
            return result.length;
        }

        @Override
        public Object getItem(int position) {
// TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
// TODO Auto-generated method stub
            return position;
        }


    public class Holder
        {
            TextView textViewRecipesName;
            ImageView imgViewRecipesList;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            Holder holder=new Holder();
            View view;
            view = inflater.inflate(R.layout.recipes_list_item, null);

            holder.textViewRecipesName=(TextView) view.findViewById(R.id.textViewRecipesName);
            holder.imgViewRecipesList=(ImageView) view.findViewById(R.id.imgViewRecipesList);

            holder.textViewRecipesName.setText(result[position]);
            Picasso.with(context).load(imageId[position]).into(holder.imgViewRecipesList);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
// TODO Auto-generated method stub
                    Toast.makeText(context, "You Clicked " + result[position], Toast.LENGTH_LONG).show();

                }
            });
            return view;
        }



    public RecipesAdapter(RecipesActivity recipesActivity, String[] recipes, int[] recipes_images) {
        result=recipes;
        context=recipesActivity;
        imageId=recipes_images;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
}
