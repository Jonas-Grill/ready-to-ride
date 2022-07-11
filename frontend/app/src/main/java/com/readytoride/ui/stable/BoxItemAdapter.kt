package com.readytoride.ui.stable

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.StableApi.Box

class BoxItemAdapter(private val context: StableFragment, private val dataset: List<Box>) :
    RecyclerView.Adapter<BoxItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_box)
        val textViewSize: TextView = view.findViewById(R.id.item_box_size)
        val textViewPrice: TextView = view.findViewById(R.id.item_box_price)
        val textViewNumber: TextView = view.findViewById(R.id.item_box_number)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_box, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewName.text = item.name
        holder.textViewSize.text = item.size.toString() + " qm"
        holder.textViewPrice.text = item.price.toString() + " €"
        holder.textViewNumber.text = item.count.toString()
    }

    override fun getItemCount() = dataset.size
}