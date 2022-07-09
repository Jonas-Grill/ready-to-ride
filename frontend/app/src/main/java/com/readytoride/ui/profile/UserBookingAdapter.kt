package com.readytoride.ui.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.LessonApi.LessonEntity
import com.readytoride.ui.booking.BookingViewModel

class UserBookingAdapter(private val context: ProfileFragment, private val dataset: List<LessonEntity>):
    RecyclerView.Adapter<UserBookingAdapter.ItemViewHolder>() {

    private lateinit var viewModel: ProfileViewModel

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val trainerHorse: TextView = view.findViewById(R.id.horse_trainer_profile_bookings)
        val timeframe: TextView = view.findViewById(R.id.booking_timeframe_profile_bookings)
        val location: TextView = view.findViewById(R.id.booking_location_profile_bookings)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_user_profile_bookings, parent, false)
        viewModel = ViewModelProvider(this.context).get(ProfileViewModel::class.java)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val names = item.trainer + ", " + item.horse.name
        val timeframe = item.startHour.toString() + ":00 Uhr - " + (item.startHour + 1).toString() + ":00 Uhr"
        holder.trainerHorse.text = names
        holder.timeframe.text = timeframe
        holder.location.text = item.arena
    }

    override fun getItemCount() = dataset.size
}