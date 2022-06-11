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
import com.readytoride.R
import com.readytoride.ui.home.HomeFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserRegisterFragment : Fragment() {

    private lateinit var user_height: EditText
    private lateinit var user_weight: EditText
    private lateinit var user_age: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_user_register, container, false)

        user_height = view.findViewById(R.id.user_height)
        user_age = view.findViewById(R.id.user_age)
        user_weight = view.findViewById(R.id.user_weight)

        view.findViewById<Button>(R.id.btn_finish_user).setOnClickListener {
            validateInput()
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(HomeFragment(), true)
        }

        return view
    }

    fun validateInput() {
        when {
            TextUtils.isEmpty(user_height.text.toString().trim()) -> {
                user_height.setError("Bitte Alter eintragen")
            }
            user_height.toString().isNotEmpty()&&user_weight.toString().isNotEmpty()&&user_age.toString().isNotEmpty() -> {
                if (user_height.text.toString().matches(Regex("[0-9]"))) {
                    Toast.makeText(context,"Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                } else {
                    user_height.setError("Bitte gültige Zahl eintragen")
                }
                if (user_weight.text.toString().matches(Regex("[0-9]"))) {
                    Toast.makeText(context,"Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                } else {
                    user_weight.setError("Bitte gültige Zahl eintragen")
                }
                if (user_age.text.toString().matches(Regex("[0-9]"))) {
                    Toast.makeText(context,"Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                } else {
                    user_age.setError("Bitte gültige Zahl eintragen")
                }
            }
        }
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