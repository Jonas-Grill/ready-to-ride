package com.readytoride.ui.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.findNavController
import com.readytoride.R
import com.readytoride.ui.home.HomeFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AdminRegisterFragment : Fragment() {
    lateinit var admin_age: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_admin_register, container, false)

        admin_age = view.findViewById(R.id.admin_age)

        view.findViewById<Button>(R.id.btn_finish_admin).setOnClickListener {
            if (validateInput()) {
                view.findNavController().navigate(R.id.nav_home)
            }
        }
        return view
    }

    fun validateInput(): Boolean {
        when {
            TextUtils.isEmpty(admin_age.text.toString().trim()) -> {
                admin_age.setError("Bitte Alter eintragen")
                return false
            }
            admin_age.toString().isNotEmpty() -> {
                if (admin_age.text.toString().matches(Regex("[0-9]"))) {
                    Toast.makeText(context, "Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                } else {
                    admin_age.setError("Bitte gültige Zahl eintragen")
                    return false
                }
            }
        }
        return true

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