package com.example.healthylifestyleapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.R.layout.row_item_for_recipes
import com.squareup.picasso.Picasso
import java.util.*

internal class MyCustomAdapter(data: ArrayList<String>, var mContext: Context) :
    ArrayAdapter<String>(mContext, R.layout.row_item_for_recipes, data) {


    private val dataSet = ArrayList<String>()
    var images: IntArray? = null


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
    private class ViewHolder {
        internal var txtName: TextView? = null
        internal var ImageviewRecipesList: ImageView? = null

    }

    init {
        this.dataSet.clear()
        this.dataSet.addAll(data)

    }

    override fun getItem(position: Int): String? {

        return dataSet[position]
    }

    /* @Override
    public Object getItem(int position) {
// TODO Auto-generated method stub
        return position;
    }*/
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val viewHolder: ViewHolder // view lookup cache stored in tag

        if (convertView == null) {

            viewHolder = ViewHolder()
            val inflater = LayoutInflater.from(context)
            convertView = inflater.inflate(row_item_for_recipes, parent, false)
            viewHolder.txtName = convertView!!.findViewById<View>(R.id.name) as TextView
            viewHolder.ImageviewRecipesList = convertView.findViewById(R.id.ImageviewRecipesList)
            Picasso.get().load(recipes_images[position])
                .into(viewHolder.ImageviewRecipesList)

            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        //viewHolder.txtName.setText((CharSequence) dataSet.get(position));
        viewHolder.txtName!!.text = dataSet[position].toString()
        convertView.setOnClickListener {
            // TODO Auto-generated method stub
            Toast.makeText(context, "You Clicked " + dataSet[position], Toast.LENGTH_SHORT).show()
            //  Intent newIntent = new Intent(getContext(), NextRecipesActivity.class);
        }

        //viewHolder.txtName.setText(getItem(position));
        //        viewHolder.ImageviewRecipesList.setImageResource(recipes_images.toString().indexOf(position));

        // Return the completed view to render on screen
        return convertView
    }

    companion object {
        var recipes_images = intArrayOf(
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak,
            R.drawable.grilled_steak
        )
    }
}
