package com.readytoride.ui.editboxes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.ui.stable.Box

class BoxesSelectionItemAdapter(
    private val context: EditBoxesFragment,
    private val dataset: List<Box>
) :
    RecyclerView.Adapter<BoxesSelectionItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkBox_box)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_selection_box, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.checkbox.text = context.resources.getString(item.boxName)
        holder.checkbox.tooltipText = "1" //Muss ID von Arena bekommen
    }

    override fun getItemCount() = dataset.size
}