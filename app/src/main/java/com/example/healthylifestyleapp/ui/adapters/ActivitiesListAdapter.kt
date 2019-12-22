package com.example.healthylifestyleapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.healthylifestyleapp.R
import com.example.healthylifestyleapp.model.Activity
import kotlinx.android.synthetic.main.layout_activities_list_item.view.*

class ActivitiesListAdapter(
    private val list: ArrayList<Activity>,
    private val onDeleteClickListener: OnDeleteClickListener
) :
    RecyclerView.Adapter<ActivitiesListAdapter.ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_activities_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val interview = list[position]
        setData(holder, interview)
    }

    private fun setData(holder: ItemViewHolder, activity: Activity) {
        holder.itemView.tvDate.text = activity.lastModifiedTimestamp
        holder.itemView.tvActivityLabel.text = activity.name
        holder.itemView.tvType.text = activity.type
        holder.itemView.tvQuantity.text = String.format("%s", activity.quantity)
        holder.itemView.btnDeleteActivity.setOnClickListener {
            onDeleteClickListener.onDelete(activity)
        }
    }


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    public interface OnDeleteClickListener {
        fun onDelete(item: Activity)
    }
}