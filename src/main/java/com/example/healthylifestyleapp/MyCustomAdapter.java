package com.example.healthylifestyleapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static com.example.healthylifestyleapp.R.layout.row_item_for_recipes;

class MyCustomAdapter extends ArrayAdapter {


    private ArrayList<Fragment> dataSet= new ArrayList<>();
    int images[];
    Context mContext;
    public static int [] recipes_images={R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak};


   /* public MyCustomAdapter(ArrayList stringList, int recipes_images, FragmentActivity activity) {
        super(stringList,activity,recipes_images[]);
        this.dataSet = stringList;
        this.mContext = activity;
        this.images=recipes_images;

    }*/
/*
    public MyCustomAdapter( ArrayList stringList, int[] recipes_images) {
        super(stringList,recipes_images);
        dataSet=stringList;
       // mContext=context;
        images=recipes_images;
        *//*inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);*//*

    }*/

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        ImageView ImageviewRecipesList;

    }

    public MyCustomAdapter(ArrayList data, Context context) {
        super(context, R.layout.row_item_for_recipes, data);
        this.dataSet = data;
        this.mContext = context;

    }

    @Nullable
    @Override
    public String getItem(int position) {

        return String.valueOf(dataSet.get(position));
    }
   /* @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }*/
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(row_item_for_recipes, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.ImageviewRecipesList= convertView.findViewById(R.id.ImageviewRecipesList);
            Picasso.with(getContext()).load( recipes_images[position]).into(viewHolder.ImageviewRecipesList);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        //viewHolder.txtName.setText((CharSequence) dataSet.get(position));
        viewHolder.txtName.setText(String.valueOf(dataSet.get(position)));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                Toast.makeText(getContext(), "You Clicked " + dataSet.get(position), Toast.LENGTH_SHORT).show();
              //  Intent newIntent = new Intent(getContext(), NextRecipesActivity.class);



            }
        });

        //viewHolder.txtName.setText(getItem(position));
//        viewHolder.ImageviewRecipesList.setImageResource(recipes_images.toString().indexOf(position));

        // Return the completed view to render on screen
        return convertView;
    }
}
