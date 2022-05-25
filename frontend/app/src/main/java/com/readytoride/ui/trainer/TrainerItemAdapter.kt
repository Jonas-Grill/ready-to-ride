package com.readytoride.ui.trainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.ui.horse.HorseFragmentDirections

class TrainerItemAdapter(private val context: TrainerFragment, private val dataset: List<Trainer>) :
    RecyclerView.Adapter<TrainerItemAdapter.ItemViewHolder>(), View.OnClickListener {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_trainer)
        val textViewHeight: TextView = view.findViewById(R.id.item_qualification)
        val textViewAge: TextView = view.findViewById(R.id.item_age)
        val textViewFocus: TextView = view.findViewById(R.id.item_focus)
        val imageView: ImageView = view.findViewById(R.id.item_trainerimage)
        val button: Button = view.findViewById(R.id.button_trainer_details)

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
        holder.textViewAge.text = context.resources.getString(item.ageStringResourceId)
        holder.textViewFocus.text = context.resources.getString(item.focusStringResourceId)
        holder.imageView.setImageResource(item.trainerimageResourceId[0])

        holder.button.tooltipText = context.resources.getString(item.trainerId)

        holder.button.setOnClickListener(this)
    }

    override fun getItemCount() = dataset.size

    override fun onClick(view: View) {
        val trainerid: String = view.tooltipText.toString()
        val action = TrainerFragmentDirections.actionNavTrainerToTrainerDetail2(trainerid)
        view.findNavController().navigate(action)
    }
}