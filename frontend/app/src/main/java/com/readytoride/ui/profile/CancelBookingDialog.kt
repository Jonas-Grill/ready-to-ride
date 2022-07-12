package com.readytoride.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.readytoride.R
import com.readytoride.ui.home.HomeFragment
import com.readytoride.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.cancel_booking_dialog.*
import kotlinx.android.synthetic.main.fragment_profile.*

class CancelBookingDialog(val fragment: ProfileFragment, val lessonId: String, val token: String): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.cancel_booking_dialog, container, false)

        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        profileViewModel.cancelMsg.observe(viewLifecycleOwner) {
            this.dismiss()
            fragment.childFragmentManager
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val deleteButton = delete_button
        val cancelButton = cancel_button

        deleteButton.setOnClickListener {
            val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
            profileViewModel.cancelUserBooking(token, lessonId)
        }

        cancelButton.setOnClickListener {
            this.dismiss()
        }
    }

    override fun onDestroy() {
        val fragTransaction = fragment.parentFragmentManager.beginTransaction()
        fragTransaction.detach(fragment)
        fragTransaction.attach(fragment)
        fragTransaction.commit()
        super.onDestroy()
    }
}