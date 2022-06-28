package com.readytoride.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.readytoride.R
import com.readytoride.databinding.FragmentHomeBinding
import com.readytoride.databinding.FragmentProfileBinding
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.ui.home.HomeFragment
import com.readytoride.ui.login.FragmentNavigation
import com.readytoride.ui.login.LoginFragment
import com.readytoride.ui.login.LoginViewModel
import com.readytoride.ui.login.RegisterFragment

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        binding.loginProfile.setOnClickListener {
            if(binding.loginProfile.text == "Anmelden") {
                it.findNavController().navigate(R.id.nav_login)
            }
            else {
                val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
                if (sharedPref != null) {
                    sharedPref.edit().remove("token").commit()
                };
                binding.loginProfile.text = "Anmelden"
                binding.headerName.visibility = View.GONE
                binding.fieldName.visibility = View.GONE
                binding.headerMail.visibility = View.GONE
                binding.fieldMail.visibility = View.GONE
                binding.headerAge.visibility = View.GONE
                binding.fieldAge.visibility = View.GONE
                //User only
                binding.headerHeight.visibility = View.GONE
                binding.fieldHeight.visibility = View.GONE
                binding.headerWeight.visibility = View.GONE
                binding.fieldWeight.visibility = View.GONE
                binding.headerProficiency.visibility = View.GONE
                binding.fieldProficiency.visibility = View.GONE

                binding.infoText.visibility = View.VISIBLE
            }

        }

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "DefaultValue")
        if(token != "DefaultValue") {
            binding.loginProfile.text = "Abmelden"
            binding.headerName.visibility = View.VISIBLE
            binding.fieldName.visibility = View.VISIBLE
            binding.headerMail.visibility = View.VISIBLE
            binding.fieldMail.visibility = View.VISIBLE
            binding.headerAge.visibility = View.VISIBLE
            binding.fieldAge.visibility = View.VISIBLE
            binding.infoText.visibility = View.GONE

            val stringToken: String = token.toString()
            profileViewModel.getUserData(stringToken)
        }

        profileViewModel.user.observe(viewLifecycleOwner) {
            //Everytime there are any changes to the observing instance, this code will be called

            if(it.role == "User") {
                //Set Height
                binding.headerHeight.visibility = View.VISIBLE
                binding.fieldHeight.visibility = View.VISIBLE
                binding.fieldHeight.text = it.height.toString() + "cm"

                //Set Weight
                binding.headerWeight.visibility = View.VISIBLE
                binding.fieldWeight.visibility = View.VISIBLE
                binding.fieldWeight.text = it.weight.toString() + "kg"

                //Set Proficiency
                binding.headerProficiency.visibility = View.VISIBLE
                binding.fieldProficiency.visibility = View.VISIBLE
                binding.fieldProficiency.text = it.proficiency
            }
            else if(it.role == "Trainer") {
                //Set Focus
                binding.headerFocus.visibility = View.VISIBLE
                binding.fieldFocus.visibility = View.VISIBLE
                binding.fieldFocus.text = it.focus

                //Set Description
                binding.headerDescription.visibility = View.VISIBLE
                binding.fieldDescription.visibility = View.VISIBLE
                binding.fieldDescription.text = it.description

                //Set Achievements
                binding.headerAchievements.visibility = View.VISIBLE
                //binding.fieldFocus.visibility = View.VISIBLE
                //binding.fieldFocus.text = it.focus

                //Set Certificates
                binding.headerCertifiates.visibility = View.VISIBLE
                //binding.fieldFocus.visibility = View.VISIBLE
                //binding.fieldFocus.text = it.focus
            }
            else if(it.role == "Admin" || it.role == "Trainer") {
                //SET RolePasscode

                //binding.headerDescription.visibility = View.VISIBLE
                //binding.fieldDescription.visibility = View.VISIBLE
                //binding.fieldDescription.text = it.description
            }

            binding.fieldName.text = it.name.firstName + " " + it.name.lastName
            binding.fieldMail.text = it.email
            binding.fieldAge.text = it.age.toString()

        }
        //textView.text = token

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}