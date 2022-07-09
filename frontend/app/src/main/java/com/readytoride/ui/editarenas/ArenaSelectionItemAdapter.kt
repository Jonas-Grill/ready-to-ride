package com.readytoride.ui.editarenas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.StableApi.Arena

class ArenaSelectionItemAdapter(
    private val context: EditArenasFragment,
    private val dataset: List<Arena>
) :
    RecyclerView.Adapter<ArenaSelectionItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkBox_arena)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_selection_arena, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.checkbox.text = item.name
        holder.checkbox.tooltipText = "item._id" //Muss ID von Arena bekommen
    }

    override fun getItemCount() = dataset.size
}
