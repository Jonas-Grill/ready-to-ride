package com.readytoride.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.ui.horse.Horse

class BookingHorseItemAdapter(
    private val context: BookingFragment,
    private val dataset: List<Horse>,
    private val horseId: String,
    ) :
    RecyclerView.Adapter<BookingHorseItemAdapter.ItemViewHolder>() {

    private lateinit var viewModel: BookingViewModel

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkBox_trainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_selection_horse, parent, false)
        viewModel = ViewModelProvider(context).get(BookingViewModel::class.java)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.checkbox.text = context.resources.getString(item.nameStringResourceId)
        holder.checkbox.tooltipText = context.resources.getString(item.horseId)

        holder.checkbox.setOnClickListener {
            val horseid = holder.checkbox.tooltipText.toString()
            if (viewModel.isInHorses(horseid)) {
                viewModel.removeHorse(horseid)
            } else {
                viewModel.addHorse(horseid)
            }
        }

        if (holder.checkbox.tooltipText == horseId) {
            holder.checkbox.isChecked = true
            viewModel.addHorse(horseId)
        }
    }

    override fun getItemCount() = dataset.size
}