package com.readytoride.ui.horse

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.readytoride.R

class HorseItemAdapter(private val context: HorseFragment, private val dataset: List<Horse>) : RecyclerView.Adapter<HorseItemAdapter.ItemViewHolder>(), View.OnClickListener{

    class ItemViewHolder( val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_horsename_overview)
        val textViewHeight: TextView = view.findViewById(R.id.item_horseheight_overview)
        val textViewRace: TextView = view.findViewById(R.id.item_race_overview)
        val textViewAge: TextView = view.findViewById(R.id.item_horseage_overview)
        val textViewColour: TextView = view.findViewById(R.id.item_colour_overview)
        val textViewDifficulty: TextView = view.findViewById(R.id.item_difficulty_overview)
        val imageView: ImageView = view.findViewById(R.id.item_horseimage_overview)
        val button: Button = view.findViewById(R.id.button_horse_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_horse, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewName.text = context.resources.getString(item.nameStringResourceId)
        holder.textViewHeight.text = context.resources.getString(item.heightStringResourceId)
        holder.textViewRace.text = context.resources.getString(item.raceStringResourceId)
        holder.textViewAge.text = context.resources.getString(item.ageStringResourceId)
        holder.textViewColour.text = context.resources.getString(item.colourStringResourceId)
        holder.textViewDifficulty.text = context.resources.getString(item.difficultyStringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)

        holder.button.setOnClickListener {
            holder.button.setOnClickListener(this);

        }


    }

    override fun getItemCount() = dataset.size

    override fun onClick(view: View) {
        Navigation.findNavController(view).navigate(R.id.action_nav_horses_to_nav_horse_detail)
    }
}