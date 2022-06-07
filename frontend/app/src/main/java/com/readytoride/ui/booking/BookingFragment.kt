package com.readytoride.ui.booking

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.ui.horse.HorseDatasource
import com.readytoride.ui.trainer.TrainerDatasource

class BookingFragment : Fragment(), View.OnClickListener{

    companion object {
        fun newInstance() = BookingFragment()
        var expand = true
    }

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDatasetHorse = HorseDatasource().loadHorses()
        val recyclerViewHorse = view.findViewById<RecyclerView>(R.id.recycler_view_selection_horse)
        recyclerViewHorse.adapter = BookingHorseItemAdapter(this, myDatasetHorse)
        recyclerViewHorse.setHasFixedSize(true)

        val myDatasetTrainer = TrainerDatasource().loadTrainer()
        val recyclerViewTrainer = view.findViewById<RecyclerView>(R.id.recycler_view_selection_trainer)
        recyclerViewTrainer.adapter = BookingTrainerItemAdapter(this, myDatasetTrainer)
        recyclerViewTrainer.setHasFixedSize(true)

        val myDatasetMondayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day1 }
        val recyclerViewBookingsMonday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_monday)
        recyclerViewBookingsMonday.adapter = BookingSlotItemAdapter(this, myDatasetMondayBookings)
        recyclerViewBookingsMonday.setHasFixedSize(true)

        val myDatasetTuesdayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day2 }
        val recyclerViewBookingsTuesday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_tuesday)
        recyclerViewBookingsTuesday.adapter = BookingSlotItemAdapter(this, myDatasetTuesdayBookings)
        recyclerViewBookingsTuesday.setHasFixedSize(true)

        val myDatasetWednesdayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day3 }
        val recyclerViewBookingsWednesday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_wednesday)
        recyclerViewBookingsWednesday.adapter = BookingSlotItemAdapter(this, myDatasetWednesdayBookings)
        recyclerViewBookingsWednesday.setHasFixedSize(true)

        val myDatasetThursdayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day4 }
        val recyclerViewBookingsThursday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_thursday)
        recyclerViewBookingsThursday.adapter = BookingSlotItemAdapter(this, myDatasetThursdayBookings)
        recyclerViewBookingsThursday.setHasFixedSize(true)

        val myDatasetFridayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day5 }
        val recyclerViewBookingsFriday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_friday)
        recyclerViewBookingsFriday.adapter = BookingSlotItemAdapter(this, myDatasetFridayBookings)
        recyclerViewBookingsFriday.setHasFixedSize(true)

        val myDatasetSaturdayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day6 }
        val recyclerViewBookingsSaturday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_saturday)
        recyclerViewBookingsSaturday.adapter = BookingSlotItemAdapter(this, myDatasetSaturdayBookings)
        recyclerViewBookingsSaturday.setHasFixedSize(true)

        val myDatasetSundayBookings = BookingsDatasource().loadBookings().filter { it.bookingDay == R.string.day7 }
        val recyclerViewBookingsSunday = view.findViewById<RecyclerView>(R.id.recycler_view_bookings_sunday)
        recyclerViewBookingsSunday.adapter = BookingSlotItemAdapter(this, myDatasetSundayBookings)
        recyclerViewBookingsSunday.setHasFixedSize(true)

        val buttonExpand: Button = view.findViewById(R.id.button6)
        buttonExpand.setOnClickListener(this)

        val expandedView: LinearLayout = view.findViewById(R.id.expanded_view)
        expandedView.visibility = View.GONE
    }

    override fun onClick(view: View?) {
        if (expand) {
            view?.findViewById<LinearLayout>(R.id.expanded_view)?.visibility = View.GONE
            expand = false
        } else {
            view?.findViewById<LinearLayout>(R.id.expanded_view)?.visibility = View.VISIBLE
            expand = true
        }
    }

}