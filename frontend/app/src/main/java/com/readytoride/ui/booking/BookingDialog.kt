package com.readytoride.ui.booking

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.readytoride.R

class BookingDialog : DialogFragment() {

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        return inflater.inflate(com.readytoride.R.layout.fragment_booking_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstMessage: CheckBox = view.findViewById(R.id.layout_agreement)
        val secondMessage: LinearLayout = view.findViewById(R.id.layout_success_message)
        val failMessage: LinearLayout = view.findViewById(R.id.linear_layout_fail)
        val submitButton: Button = view.findViewById(R.id.booking_button)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val role: String? = sharedPref?.getString("role", "defaultRole")
        if (role == "defaultRole") {
            failMessage.visibility = View.VISIBLE
            firstMessage.visibility = View.GONE
            submitButton.visibility = View.GONE
            secondMessage.visibility = View.GONE
        } else {
            failMessage.visibility = View.GONE
            secondMessage.visibility = View.GONE
            println("1: "+submitButton.isClickable)
        }

        firstMessage.setOnClickListener {
            println("2: "+submitButton.isClickable)
            submitButton.isClickable = !submitButton.isClickable
            println("3: "+submitButton.isClickable)
        }

        submitButton.setOnClickListener {
            firstMessage.visibility = View.GONE
            submitButton.visibility = View.GONE
            secondMessage.visibility = View.VISIBLE

            viewModel.bookLesson()

        }

        submitButton.isClickable = false

    }
}