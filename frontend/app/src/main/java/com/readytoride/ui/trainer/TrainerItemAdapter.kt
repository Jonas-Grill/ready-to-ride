package com.readytoride.ui.trainer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.DownloadImageTask
import com.readytoride.R
import com.readytoride.network.UserApi.Accomplishment
import com.readytoride.network.UserApi.Name
import com.readytoride.network.UserApi.UserEntity

class TrainerItemAdapter(private val context: TrainerFragment, private val dataset: List<UserEntity>) :
    RecyclerView.Adapter<TrainerItemAdapter.ItemViewHolder>(), View.OnClickListener {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val textViewName: TextView = view.findViewById(R.id.item_trainer)
        val textViewQualification: TextView = view.findViewById(R.id.item_qualification)
        val textViewAge: TextView = view.findViewById(R.id.item_age)
        val textViewFocus: TextView = view.findViewById(R.id.item_focus)
        val imageView: ImageView = view.findViewById(R.id.item_trainerimage)
        val button: Button = view.findViewById(R.id.button_trainer_details)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_trainer, parent, false)

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]

        val name: Name = item.name
        val nameString: String = name.firstName + " " + name.lastName
        holder.textViewName.text = nameString

        val certificates: List<Accomplishment> = item.certificates
        var year: Int = 0
        var qual: String = ""
        for (certificate in certificates) {
            if (certificate.year > year){
                year = certificate.year
                qual = certificate.name
            }
        }

        holder.textViewQualification.text = qual

        holder.textViewAge.text = item.age.toString()
        holder.textViewFocus.text = item.focus
        DownloadImageTask(holder.imageView)
            .execute("https://ready-to-ride-backend.tk/images/" + item.profilePicture)
        holder.button.tooltipText = item._id
        holder.button.setOnClickListener(this)
    }

    override fun getItemCount() = dataset.size

    override fun onClick(view: View) {
        val trainerid: String = view.tooltipText.toString()
        val action = TrainerFragmentDirections.actionNavTrainerToTrainerDetail2(trainerid)
        view.findNavController().navigate(action)
    }
}