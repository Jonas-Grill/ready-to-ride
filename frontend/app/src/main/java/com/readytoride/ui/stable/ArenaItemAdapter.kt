package com.readytoride.ui.stable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.StableApi.Arena

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
        holder.textViewName.text = item.name
        holder.textViewSize.text = item.size.toString() + " qm"
    }

    override fun getItemCount() = dataset.size
}