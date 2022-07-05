package com.readytoride.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ScrollView
import android.widget.TextView
import android.widget.ViewSwitcher
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.readytoride.R
import com.readytoride.databinding.FragmentHomeBinding
import com.readytoride.databinding.FragmentProfileBinding
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.PostingLessonEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.ui.home.HomeFragment
import com.readytoride.ui.login.FragmentNavigation
import com.readytoride.ui.login.LoginFragment
import com.readytoride.ui.login.LoginViewModel
import com.readytoride.ui.login.RegisterFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.util.*

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

        binding.editProfile.setOnClickListener {
            onEditProfile()
        }

        binding.loginProfile.setOnClickListener {
            if(binding.loginProfile.text == "Anmelden") {
                it.findNavController().navigate(R.id.nav_login)
            }
            else {
                val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
                if (sharedPref != null) {
                    sharedPref.edit().remove("token").commit()
                    sharedPref.edit().remove("role").commit()
                };
                binding.loginProfile.text = "Anmelden"
                binding.headerName.visibility = View.GONE
                binding.nameSwitcher.visibility = View.GONE
                binding.headerMail.visibility = View.GONE
                binding.mailSwitcher.visibility = View.GONE
                binding.headerAge.visibility = View.GONE
                binding.ageSwitcher.visibility = View.GONE
                //User only
                binding.headerHeight.visibility = View.GONE
                binding.heightSwitcher.visibility = View.GONE
                binding.headerWeight.visibility = View.GONE
                binding.weightSwitcher.visibility = View.GONE
                binding.headerProficiency.visibility = View.GONE
                binding.proficiencySwitcher.visibility = View.GONE
                binding.bookedHoursLayout.visibility = View.GONE

                //Admin only
                binding.adminRegisterLayout.visibility = View.GONE
                binding.headerAdminPasscode.visibility = View.GONE
                binding.fieldAdminPasscode.visibility = View.GONE
                binding.headerTrainerPasscode.visibility = View.GONE
                binding.fieldTrainerPasscode.visibility = View.GONE

                //Trainer only
                binding.headerFocus.visibility = View.GONE
                binding.focusSwitcher.visibility = View.GONE
                binding.headerDescription.visibility = View.GONE
                binding.descriptionSwitcher.visibility = View.GONE
                binding.headerAchievements.visibility = View.GONE
                binding.headerCertifiates.visibility = View.GONE

                binding.editProfile.visibility = View.GONE
                binding.infoText.visibility = View.VISIBLE
            }

        }

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "DefaultValue")
        if(token != "DefaultValue") {
            binding.loginProfile.text = "Abmelden"
            binding.headerName.visibility = View.VISIBLE
            binding.nameSwitcher.visibility = View.VISIBLE
            binding.headerMail.visibility = View.VISIBLE
            binding.mailSwitcher.visibility = View.VISIBLE
            binding.headerAge.visibility = View.VISIBLE
            binding.ageSwitcher.visibility = View.VISIBLE

            binding.editProfile.visibility = View.VISIBLE
            binding.infoText.visibility = View.GONE


            val stringToken: String = token.toString()
            profileViewModel.getUserData(stringToken)
        }

        profileViewModel.user.observe(viewLifecycleOwner) {
            //Everytime there are any changes to the observing instance, this code will be called
            val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
            val role: String? = sharedPref?.getString("role", "defaultRole")
            if(role == "User") {
                //Set Height
                binding.headerHeight.visibility = View.VISIBLE
                binding.heightSwitcher.visibility = View.VISIBLE
                binding.fieldHeight.text = it.height.toString() + "cm"
                binding.editHeight.setText(it.height.toString() + "cm")

                //Set Weight
                binding.headerWeight.visibility = View.VISIBLE
                binding.weightSwitcher.visibility = View.VISIBLE
                binding.fieldWeight.text = it.weight.toString() + "kg"
                binding.editWeight.setText(it.weight.toString() + "kg")

                //Set Proficiency
                binding.headerProficiency.visibility = View.VISIBLE
                binding.proficiencySwitcher.visibility = View.VISIBLE
                binding.fieldProficiency.text = it.proficiency
                binding.editProficiency.setText(it.proficiency)

                //Set Booked Hours
                binding.bookedHoursLayout.visibility = View.VISIBLE
            }
            else if(role == "Trainer") {
                //Set Focus
                binding.headerFocus.visibility = View.VISIBLE
                binding.focusSwitcher.visibility = View.VISIBLE
                binding.fieldFocus.text = it.focus
                binding.editFocus.setText(it.focus)

                //Set Description
                binding.headerDescription.visibility = View.VISIBLE
                binding.descriptionSwitcher.visibility = View.VISIBLE
                binding.fieldDescription.text = it.description
                binding.editDescription.setText(it.description)

                //Set Achievements
                binding.headerAchievements.visibility = View.VISIBLE
                //binding.fieldFocus.visibility = View.VISIBLE
                //binding.fieldFocus.text = it.focus

                //Set Certificates
                binding.headerCertifiates.visibility = View.VISIBLE
                //binding.fieldFocus.visibility = View.VISIBLE
                //binding.fieldFocus.text = it.focus
            }
            else if(role == "Admin") {
                //SET RolePasscode
                binding.adminRegisterLayout.visibility = View.VISIBLE
                binding.headerAdminPasscode.visibility = View.VISIBLE
                binding.fieldAdminPasscode.visibility = View.VISIBLE
                binding.headerTrainerPasscode.visibility = View.VISIBLE
                binding.fieldTrainerPasscode.visibility = View.VISIBLE
            }

            binding.fieldName.text = it.name.firstName + " " + it.name.lastName
            binding.editName.setText(it.name.firstName + " " + it.name.lastName)
            binding.fieldMail.text = it.email
            binding.editMail.setText(it.email)
            binding.fieldAge.text = it.age.toString()
            binding.editAge.setText(it.age.toString())

        }
        //textView.text = token

        return root
    }

    fun onEditProfile() {
        val nameSwitcher: ViewSwitcher = binding.nameSwitcher
        nameSwitcher.showNext()
        val mailSwitcher: ViewSwitcher = binding.mailSwitcher
        mailSwitcher.showNext()
        val ageSwitcher: ViewSwitcher = binding.ageSwitcher
        ageSwitcher.showNext()
        //User only
        val heightSwitcher: ViewSwitcher = binding.heightSwitcher
        heightSwitcher.showNext()
        val weightSwitcher: ViewSwitcher = binding.weightSwitcher
        weightSwitcher.showNext()
        val proficiencySwitcher: ViewSwitcher = binding.proficiencySwitcher
        proficiencySwitcher.showNext()
        //Trainer only
        val focusSwitcher: ViewSwitcher = binding.focusSwitcher
        focusSwitcher.showNext()
        val descriptionSwitcher: ViewSwitcher = binding.descriptionSwitcher
        descriptionSwitcher.showNext()

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val role: String? = sharedPref?.getString("role", "defaultRole")
        val horse: String = sharedPref?.getString("lesson", "Nothing").toString()
        val gson: Gson = Gson()
        val lesson: PostingLessonEntity = gson.fromJson<PostingLessonEntity>(horse, PostingLessonEntity::class.java)


        binding.stickyScroll.fullScroll(ScrollView.FOCUS_UP)
        if(binding.editProfile.text == "Profil bearbeiten") {
            binding.loginProfile.visibility = View.GONE
            binding.cancelEditProfile.visibility = View.VISIBLE
            binding.editProfile.text = "Profil speichern"
            if(role == "User") {
                binding.bookedHoursLayout.visibility = View.GONE
            }
            else if(role == "Admin") {
                binding.adminRegisterLayout.visibility = View.GONE
                binding.headerAdminPasscode.visibility = View.GONE
                binding.fieldAdminPasscode.visibility = View.GONE
                binding.headerTrainerPasscode.visibility = View.GONE
                binding.fieldTrainerPasscode.visibility = View.GONE
            }
        }
        else {
            binding.loginProfile.visibility = View.VISIBLE
            binding.cancelEditProfile.visibility = View.GONE
            binding.editProfile.text = "Profil bearbeiten"
            if(role == "User") {
                binding.bookedHoursLayout.visibility = View.VISIBLE
            }
            else if(role == "Admin") {
                binding.adminRegisterLayout.visibility = View.VISIBLE
                binding.headerAdminPasscode.visibility = View.VISIBLE
                binding.fieldAdminPasscode.visibility = View.VISIBLE
                binding.headerTrainerPasscode.visibility = View.VISIBLE
                binding.fieldTrainerPasscode.visibility = View.VISIBLE
            }

        }
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