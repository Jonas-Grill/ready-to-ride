package com.readytoride.ui.trainerdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R

class AchievementsItemAdapter (private val context: TrainerDetail, private val dataset: List<List<Int>>) : RecyclerView.Adapter<AchievementsItemAdapter.ItemViewHolder>(){

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.item_achievement_date)
        val textViewAchievement: TextView = view.findViewById(R.id.item_achievement_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementsItemAdapter.ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_trainer_achievements, parent, false)

        return AchievementsItemAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: AchievementsItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewDate.text = context.resources.getString(item[0])
        holder.textViewAchievement.text = context.resources.getString(item[1])
    }

    override fun getItemCount() = dataset.size
}