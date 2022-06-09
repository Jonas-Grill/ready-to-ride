package com.readytoride.ui.login

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.readytoride.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {
    private lateinit var mail: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        mail = view.findViewById(R.id.login_mail)
        password = view.findViewById(R.id.login_pwd)


        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(RegisterFragment(), false)
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            validateInput()
        }

        return view
    }

    fun validateInput() {
        when {
            TextUtils.isEmpty(mail.text.toString().trim()) -> {
                mail.setError("Bitte E-Mail eintragen")
            }
            TextUtils.isEmpty(password.text.toString().trim()) -> {
                password.setError("Bitte Passwort eintragen")
            }
            mail.text.toString().isNotEmpty() && password.toString().isNotEmpty() -> {
                if (mail.text.toString().matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"))) {

                } else {
                    mail.setError("Bitte gültige E-Mail eintragen")
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
         * @return A new instance of fragment LoginFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}