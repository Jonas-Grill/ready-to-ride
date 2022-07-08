package com.readytoride.ui.login

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.readytoride.R
import com.readytoride.network.UserApi.LoginEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.network.UserApi.UserApi
import com.readytoride.ui.home.NewsDetailDialog

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private var callback = true


class LoginFragment : Fragment() {
    private lateinit var mail: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val loginViewModel =
            ViewModelProvider(this).get(LoginViewModel::class.java)
        var view = inflater.inflate(R.layout.fragment_login, container, false)

        mail = view.findViewById(R.id.login_mail)
        password = view.findViewById(R.id.login_pwd)


        view.findViewById<Button>(R.id.btn_register).setOnClickListener {
            view.findNavController().navigate(R.id.nav_register)
        }

        view.findViewById<Button>(R.id.btn_login).setOnClickListener {
            if(validateInput()) {
                //Weiterleitung nach Login
                val loginEntity = LoginEntity(mail.text.toString().trim(), password.text.toString().trim())
                loginViewModel.loginUser(mail.text.toString().trim(), password.text.toString().trim())
            }
        }

        loginViewModel.token.observe(viewLifecycleOwner) {
            //Everytime there are any changes to the observing instance, this code will be called

            val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            if (editor != null) {
                editor.putString("token", it.token)
                editor.commit()
                editor.apply()
            }
            loginViewModel.getUserRole(it.token)
        }

        loginViewModel.role.observe(viewLifecycleOwner) {
            val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
            val editor = sharedPref?.edit()
            if (editor != null) {
                editor.putString("role", it)
                editor.commit()
                editor.apply()
            }
            view.findNavController().navigate(R.id.nav_home)
        }

        loginViewModel.error.observe(viewLifecycleOwner) {
            var dialog = FailedLoginDialog()
            dialog.show(parentFragmentManager, "FailedLoginDialog")
            mail.setText("")
            password.setText("")
        }

        return view
    }

    fun validateInput(): Boolean {
        var validater: Boolean = true
        if(TextUtils.isEmpty(mail.text.toString().trim())) {
            mail.setError("Bitte E-Mail eintragen")
            validater = false
        }
        if(TextUtils.isEmpty(password.text.toString().trim())) {
            password.setError("Bitte Passwort eintragen")
            validater = false
        }
        if(mail.text.toString().isNotEmpty() && password.toString().isNotEmpty()) {
            if(android.util.Patterns.EMAIL_ADDRESS.matcher(mail.text.toString()).matches()) {
            } else {
                mail.setError("Bitte gültige E-Mail eintragen")
                validater =  false
            }
        }
        return validater
    }

    override fun onDestroyView() {
        //while(callback) {

        //}
        super.onDestroyView()
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