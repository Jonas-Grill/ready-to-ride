package com.readytoride.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import com.readytoride.R
import com.readytoride.ui.home.HomeFragment
import com.readytoride.ui.login.FragmentNavigation
import com.readytoride.ui.login.LoginFragment
import com.readytoride.ui.login.RegisterFragment

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.findViewById<Button>(R.id.login_profile).setOnClickListener {
            view.findNavController().navigate(R.id.nav_login)
            //var navRegister = activity as FragmentNavigation
            //navRegister.navigateFrag(HomeFragment(), false)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}