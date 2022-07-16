package com.readytoride.ui.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.readytoride.R
import com.readytoride.network.UserApi.Name
import com.readytoride.network.UserApi.PostUserEntity
import com.readytoride.ui.home.HomeFragment
import com.readytoride.ui.profile.ProfileViewModel
import okhttp3.MediaType
import okhttp3.RequestBody

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserRegisterFragment : Fragment() {

    private lateinit var user_height: EditText
    private lateinit var user_weight: EditText
    private lateinit var user_age: EditText
    private lateinit var radioGroup: RadioGroup

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_register, container, false)

        val registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        user_height = view.findViewById(R.id.user_height)
        user_age = view.findViewById(R.id.user_age)
        user_weight = view.findViewById(R.id.user_weight)
        radioGroup = view.findViewById(R.id.radioGroup)
        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val mail: String = sharedPref?.getString("mail", "defaultRole").toString()
        val password: String = sharedPref?.getString("password", "defaultRole").toString()
        val name: String = sharedPref?.getString("name", "defaultRole").toString()
        val gson: Gson = Gson()
        val jsonName = gson.fromJson<Name>(name, Name::class.java)

        view.findViewById<Button>(R.id.btn_finish_user).setOnClickListener {
            val radioButtonId = radioGroup.checkedRadioButtonId
            val radioButton: RadioButton = view.findViewById<RadioButton>(radioButtonId);

            if (validateInput()) {
                val userObj: JsonObject = JsonObject()
                val nameObj: JsonObject = JsonObject()
                nameObj.addProperty("firstName", jsonName.firstName)
                nameObj.addProperty("lastName", jsonName.lastName)
                userObj.addProperty("email", mail)
                userObj.addProperty("password", password)
                userObj.addProperty("age", user_age.text.toString().toInt())
                userObj.addProperty("role", "User")
                userObj.addProperty("weight", user_weight.text.toString().toInt())
                userObj.addProperty("height", user_height.text.toString().toInt())
                userObj.addProperty("proficiency", radioButton.text.toString())
                userObj.add("name", nameObj)
                val body = RequestBody.create(MediaType.parse("application/json"), userObj.toString())
                registerViewModel.registerUser(body)
            }
        }

        registerViewModel.newUser.observe(viewLifecycleOwner) {
            view.findNavController().navigate(R.id.nav_login)
        }

        return view
    }

    fun validateInput(): Boolean {
        var validater: Boolean = true
        if(TextUtils.isEmpty(user_height.text.toString().trim())) {
            user_height.setError("Bitte Größe eintragen")
            validater = false
        }
        if(TextUtils.isEmpty(user_weight.text.toString().trim())) {
            user_weight.setError("Bitte Gewicht eintragen")
            validater = false
        }
        if(TextUtils.isEmpty(user_age.text.toString().trim())) {
            user_age.setError("Bitte Alter eintragen")
            validater = false
        }
        return validater
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserRegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserRegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}