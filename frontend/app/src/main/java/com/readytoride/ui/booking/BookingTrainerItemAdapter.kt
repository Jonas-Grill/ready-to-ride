package com.readytoride.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.UserApi.UserEntity

class BookingTrainerItemAdapter(
    private val context: BookingFragment,
    private val dataset: MutableList<UserEntity>
) :
    RecyclerView.Adapter<BookingTrainerItemAdapter.ItemViewHolder>() {

    private lateinit var viewModel: BookingViewModel

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val checkbox: CheckBox = view.findViewById(R.id.checkBox_trainer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_selection_trainer, parent, false)
        viewModel = ViewModelProvider(context).get(BookingViewModel::class.java)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val name = item.name
        holder.checkbox.text = name.firstName + " " + name.lastName
        holder.checkbox.tooltipText = item._id

        holder.checkbox.setOnClickListener {
            val trainerid = holder.checkbox.tooltipText.toString()
            if (viewModel.isInTrainers(trainerid)) {
                viewModel.removeTrainer(trainerid)
            } else {
                viewModel.addTrainer(trainerid)
            }
        }
    }

    override fun getItemCount() = dataset.size
}