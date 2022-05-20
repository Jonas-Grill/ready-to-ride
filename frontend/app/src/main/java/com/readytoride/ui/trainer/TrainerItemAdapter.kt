package com.readytoride.ui.trainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R

class TrainerItemAdapter(private val context: TrainerFragment, private val dataset: List<Trainer>) : RecyclerView.Adapter<TrainerItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_trainer)
        val textViewHeight: TextView = view.findViewById(R.id.item_qualification)
        val imageView: ImageView = view.findViewById(R.id.item_trainerimage)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_trainer, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewName.text = context.resources.getString(item.trainerStringResourceId)
        holder.textViewHeight.text = context.resources.getString(item.qualificationStringResourceId)
        holder.imageView.setImageResource(item.trainerimageResourceId)
    }

    override fun getItemCount() = dataset.size
}