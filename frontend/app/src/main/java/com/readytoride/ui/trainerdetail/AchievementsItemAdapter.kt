package com.readytoride.ui.trainerdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.UserApi.Accomplishment

class AchievementsItemAdapter(
    private val context: TrainerDetail,
    private val dataset: List<Accomplishment>
) : RecyclerView.Adapter<AchievementsItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewDate: TextView = view.findViewById(R.id.item_achievement_date)
        val textViewAchievement: TextView = view.findViewById(R.id.item_achievement_name)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AchievementsItemAdapter.ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_trainer_achievements, parent, false)

        return AchievementsItemAdapter.ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: AchievementsItemAdapter.ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textViewDate.text = context.resources.getString(item.year)
        holder.textViewAchievement.text = item.name
    }

    override fun getItemCount() = dataset.size
}