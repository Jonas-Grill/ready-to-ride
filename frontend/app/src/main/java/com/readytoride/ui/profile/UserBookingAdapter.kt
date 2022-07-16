package com.readytoride.ui.profile

import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.LessonApi.LessonEntity


class UserBookingAdapter(private val context: ProfileFragment, private val dataset: MutableList<LessonEntity>, private val token: String, private val role: String):
    RecyclerView.Adapter<UserBookingAdapter.ItemViewHolder>() {

    private lateinit var viewModel: ProfileViewModel

    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val trainerHorse: TextView = view.findViewById(R.id.horse_trainer_profile_bookings)
        val timeframe: TextView = view.findViewById(R.id.booking_timeframe_profile_bookings)
        val location: TextView = view.findViewById(R.id.booking_location_profile_bookings)
        val button: AppCompatImageButton = view.findViewById(R.id.cancel_button)
        val layout: LinearLayout = view.findViewById(R.id.user_booking_layout)
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

        if (role == "User") {
            holder.button.visibility = View.GONE
            val param = holder.layout.layoutParams as ViewGroup.MarginLayoutParams
            param.marginEnd = 30.toPx()
        }

        holder.button.setOnClickListener {

            //hier wird ein PopUp erzeugt mit Einverständniserklärung, ggf. Infos und Buchungsbestätigung
            var dialog = CancelBookingDialog(context, item._id, token)
            dialog.show(context.childFragmentManager, "CancelBookingDialog")
        }

    }

    override fun getItemCount() = dataset.size

    fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()
}