package com.readytoride.ui.profile

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.JsonObject
import com.readytoride.R
import com.readytoride.databinding.FragmentProfileBinding
import com.readytoride.network.LessonApi.PostingLessonEntity
import kotlinx.android.synthetic.main.fragment_profile.view.*
import okhttp3.MediaType
import okhttp3.RequestBody
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

        binding.cancelEditProfile.setOnClickListener {
            onCancelProfile()
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
                binding.editHeight.setText(it.height.toString())

                //Set Weight
                binding.headerWeight.visibility = View.VISIBLE
                binding.weightSwitcher.visibility = View.VISIBLE
                binding.fieldWeight.text = it.weight.toString() + "kg"
                binding.editWeight.setText(it.weight.toString())

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
                binding.fieldAdminPasscode.text = it.adminPasscode
                binding.fieldAdminPasscode.visibility = View.VISIBLE
                binding.headerTrainerPasscode.visibility = View.VISIBLE
                binding.fieldTrainerPasscode.text = it.trainerPasscode
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
        val token: String? = sharedPref?.getString("token", "DefaultValue")


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
            val userObj: JsonObject = JsonObject()
            val nameObj: JsonObject = JsonObject()
            val wholeName: List<String> = binding.editName.text.toString().split(" ")
            nameObj.addProperty("firstName", wholeName[0])
            nameObj.addProperty("lastName", wholeName[1])
            userObj.addProperty("age", binding.editAge.text.toString().toInt())
            userObj.addProperty("email", binding.editMail.text.toString())
            userObj.add("name", nameObj)

            binding.loginProfile.visibility = View.VISIBLE
            binding.cancelEditProfile.visibility = View.GONE
            binding.editProfile.text = "Profil bearbeiten"
            if(role == "User") {
                userObj.addProperty("height", binding.editHeight.text.toString().toInt())
                userObj.addProperty("weight", binding.editWeight.text.toString().toInt())
                userObj.addProperty("proficiency", binding.editProficiency.text.toString())

                binding.bookedHoursLayout.visibility = View.VISIBLE
            }
            else if(role == "Admin") {
                binding.adminRegisterLayout.visibility = View.VISIBLE
                binding.headerAdminPasscode.visibility = View.VISIBLE
                binding.fieldAdminPasscode.visibility = View.VISIBLE
                binding.headerTrainerPasscode.visibility = View.VISIBLE
                binding.fieldTrainerPasscode.visibility = View.VISIBLE
            }
            val body = RequestBody.create(MediaType.parse("application/json"), userObj.toString())
            val profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
            profileViewModel.updateUser(token.toString(), body)
        }
    }

    fun onCancelProfile() {
        val nameSwitcher: ViewSwitcher = binding.nameSwitcher
        nameSwitcher.showNext()
        binding.editName.setText(binding.fieldName.text)
        val mailSwitcher: ViewSwitcher = binding.mailSwitcher
        mailSwitcher.showNext()
        binding.editMail.setText(binding.fieldMail.text)
        val ageSwitcher: ViewSwitcher = binding.ageSwitcher
        ageSwitcher.showNext()
        binding.editAge.setText(binding.fieldAge.text)
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

        binding.loginProfile.visibility = View.VISIBLE
        binding.cancelEditProfile.visibility = View.GONE
        binding.editProfile.text = "Profil bearbeiten"
        if(role == "User") {
            binding.bookedHoursLayout.visibility = View.VISIBLE
            binding.editHeight.setText(binding.fieldHeight.text.split("cm")[0])
            binding.editWeight.setText(binding.fieldWeight.text.split("kg")[0])
            binding.editProficiency.setText(binding.fieldProficiency.text)
        }
        else if(role == "Admin") {
            binding.adminRegisterLayout.visibility = View.VISIBLE
            binding.headerAdminPasscode.visibility = View.VISIBLE
            binding.fieldAdminPasscode.visibility = View.VISIBLE
            binding.headerTrainerPasscode.visibility = View.VISIBLE
            binding.fieldTrainerPasscode.visibility = View.VISIBLE
        }
        else if(role == "Trainer") {
            binding.editFocus.setText(binding.fieldFocus.text)
            binding.editDescription.setText(binding.fieldDescription.text)
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