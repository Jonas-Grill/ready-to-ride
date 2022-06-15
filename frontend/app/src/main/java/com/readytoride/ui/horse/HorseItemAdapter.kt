package com.readytoride.ui.horse

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseEntity

class HorseItemAdapter(private val context: HorseFragment, private val dataset: List<HorseEntity>) :
    RecyclerView.Adapter<HorseItemAdapter.ItemViewHolder>(), View.OnClickListener {

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_horse_name)
        val textViewHeight: TextView = view.findViewById(R.id.item_horse_height)
        val textViewRace: TextView = view.findViewById(R.id.item_race_overview)
        val textViewAge: TextView = view.findViewById(R.id.item_horseage_overview)
        val textViewColour: TextView = view.findViewById(R.id.item_colour_overview)
        val textViewDifficulty: TextView = view.findViewById(R.id.item_difficulty_overview)
        val imageView: ImageView = view.findViewById(R.id.item_horse_image)
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
        holder.textViewName.text = item.name
        holder.textViewHeight.text = context.resources.getString(item.height)
        holder.textViewRace.text = item.race
        holder.textViewAge.text = context.resources.getString(item.age)
        holder.textViewColour.text = item.colour
        holder.textViewDifficulty.text = item.difficultyLevel
        holder.imageView.setImageURI(item.profilePicture.toUri())
        holder.button.tooltipText = item._id
        holder.button.setOnClickListener(this)
    }

    override fun getItemCount() = dataset.size

    override fun onClick(view: View) {
        val horseid: String = view.tooltipText.toString()
        val action = HorseFragmentDirections.actionNavHorsesToNavHorseDetail(horseid)
        view.findNavController().navigate(action)
    }
}