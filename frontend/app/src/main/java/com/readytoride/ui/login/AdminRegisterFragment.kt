package com.readytoride.ui.login

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.readytoride.R
import com.readytoride.network.UserApi.Name
import com.readytoride.ui.home.HomeFragment
import kotlinx.android.synthetic.main.fragment_admin_register.*
import okhttp3.MediaType
import okhttp3.RequestBody

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminRegisterFragment : Fragment() {
    lateinit var admin_age: EditText
    lateinit var admin_passcode_input: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_admin_register, container, false)

        val registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)

        admin_age = view.findViewById(R.id.admin_age)
        admin_passcode_input = view.findViewById(R.id.admin_passcode_input)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val mail: String = sharedPref?.getString("mail", "defaultRole").toString()
        val password: String = sharedPref?.getString("password", "defaultRole").toString()
        val name: String = sharedPref?.getString("name", "defaultRole").toString()
        val gson: Gson = Gson()
        val jsonName = gson.fromJson<Name>(name, Name::class.java)

        view.findViewById<Button>(R.id.btn_finish_admin).setOnClickListener {
            if (validateInput()) {
                val userObj: JsonObject = JsonObject()
                val nameObj: JsonObject = JsonObject()
                nameObj.addProperty("firstName", jsonName.firstName)
                nameObj.addProperty("lastName", jsonName.lastName)
                userObj.addProperty("email", mail)
                userObj.addProperty("password", password)
                userObj.addProperty("age", admin_age.text.toString().toInt())
                userObj.addProperty("role", "Admin")
                userObj.addProperty("rolePasscode", admin_passcode_input.text.toString())
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
        var validater = true
        if(TextUtils.isEmpty(admin_age.text.toString().trim())) {
            admin_age.setError("Bitte Alter eintragen")
            validater = false
        }
        if(TextUtils.isEmpty(admin_passcode_input.text.toString().trim())) {
            admin_passcode_input.setError("Bitte einen Wert eintragen")
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
         * @return A new instance of fragment AdminRegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminRegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}