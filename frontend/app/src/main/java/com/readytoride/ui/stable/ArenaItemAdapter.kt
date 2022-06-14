package com.readytoride.ui.stable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R

class ArenaItemAdapter(private val context: StableFragment, private val dataset: List<Arena>) :
    RecyclerView.Adapter<ArenaItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_arena_name)
        val textViewSize: TextView = view.findViewById(R.id.item_arena_size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_arena, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewName.text = context.resources.getString(item.arenaName)
        holder.textViewSize.text = context.resources.getString(item.arenaSize)
        println("IN ARENA")
    }

    override fun getItemCount() = dataset.size
}