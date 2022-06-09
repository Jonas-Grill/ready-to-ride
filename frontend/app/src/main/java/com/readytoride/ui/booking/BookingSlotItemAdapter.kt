package com.readytoride.ui.booking

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R

class BookingSlotItemAdapter(
    private val context: BookingFragment,
    private val dataset: List<Bookings>
) :
    RecyclerView.Adapter<BookingSlotItemAdapter.ItemViewHolder>() {

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

        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val names =
            context.resources.getString(item.bookingTrainerName) + ", " + context.resources.getString(
                item.bookingHorseName
            )
        holder.trainerHorse.text = names
        holder.timeframe.text = context.resources.getString(item.bookingTime)
        holder.location.text = context.resources.getString(item.bookingLocation)

        holder.button.setOnClickListener{
            //hier wird ein PopUp erzeugt mit Einverständniserklärung, ggf. Infos und Buchungsbestätigung
            var dialog = BookingDialog()
            dialog.show(context.childFragmentManager, "BookingDialog")

        }

    }

    override fun getItemCount() = dataset.size
}