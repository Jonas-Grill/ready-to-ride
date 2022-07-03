package com.readytoride.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.LessonApi.LessonEntity

class BookingSlotItemAdapter(
    private val context: BookingFragment,
    private val dataset: List<LessonEntity>
) :
    RecyclerView.Adapter<BookingSlotItemAdapter.ItemViewHolder>() {

    private lateinit var viewModel: BookingViewModel

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val trainerHorse: TextView = view.findViewById(R.id.horse_trainer)
        val timeframe: TextView = view.findViewById(R.id.booking_timeframe)
        val location: TextView = view.findViewById(R.id.booking_location)
        val button: Button = view.findViewById(R.id.button5)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_booking_slots, parent, false)
        viewModel = ViewModelProvider(this.context).get(BookingViewModel::class.java)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val names = item.trainer + ", " + item.horse.name
        val timeframe = item.startHour.toString() + ":00 Uhr - " + (item.startHour + 1).toString() + ":00 Uhr"
        holder.trainerHorse.text = names
        holder.timeframe.text = timeframe
        holder.location.text = item.arena

        holder.button.setOnClickListener {
            viewModel.setBookingData(item._id, item.horse.id)

            //hier wird ein PopUp erzeugt mit Einverständniserklärung, ggf. Infos und Buchungsbestätigung
            var dialog = BookingDialog()
            dialog.show(context.childFragmentManager, "BookingDialog")
        }
    }

    override fun getItemCount() = dataset.size
}