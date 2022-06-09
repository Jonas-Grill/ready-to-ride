package com.readytoride.ui.booking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import com.readytoride.R


class BookingDialog : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.readytoride.R.layout.fragment_booking_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstMessage: CheckBox = view.findViewById(R.id.layout_agreement)
        val secondMessage: LinearLayout = view.findViewById(R.id.layout_success_message)
        val submitButton: Button = view.findViewById(R.id.booking_button)

        firstMessage.setOnClickListener {
            submitButton.isClickable = !submitButton.isClickable
        }

        submitButton.setOnClickListener {
            firstMessage.visibility = View.GONE
            submitButton.visibility = View.GONE
            secondMessage.visibility = View.VISIBLE

            //Buchung durchführen
        }

        secondMessage.visibility = View.GONE
        submitButton.isClickable = false

    }
}