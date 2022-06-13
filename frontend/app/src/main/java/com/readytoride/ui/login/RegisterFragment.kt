package com.readytoride.ui.login

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.readytoride.R

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RegisterFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var option: Spinner
    lateinit var result: TextView

    private lateinit var reg_name: EditText //Vorname
    private lateinit var reg_name2: EditText //Nachname
    private lateinit var reg_mail: EditText
    private lateinit var reg_pwd1: EditText
    private lateinit var reg_pwd2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_register, container, false)

        reg_name = view.findViewById(R.id.reg_name)
        reg_name2 = view.findViewById(R.id.reg_name2)
        reg_mail = view.findViewById(R.id.reg_mail)
        reg_pwd1 = view.findViewById(R.id.reg_pwd1)
        reg_pwd2 = view.findViewById(R.id.reg_pwd2)

        //Spinner
        var item: String
        item = ""
        val spinner = view.findViewById<Spinner>(R.id.dropdown_register)
        spinner?.adapter = activity?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.roles,
                android.R.layout.simple_spinner_item
            )
        } as SpinnerAdapter
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                item = parent?.getItemAtPosition(position).toString()

            }
        }

        view.findViewById<Button>(R.id.btn_nxt).setOnClickListener {
            if (validateInput()) {
                var navRegister = activity as FragmentNavigation
                if (item == "Trainer") {
                    navRegister.navigateFrag(TrainerRegisterFragment(), true)
                } else if (item == "Nutzer") {
                    navRegister.navigateFrag(UserRegisterFragment(), true)
                } else if (item == "Admin") {
                    navRegister.navigateFrag(AdminRegisterFragment(), true)
                }
            } else {
            }
        }
        return view
    }


    fun validateInput(): Boolean {
        when {
            TextUtils.isEmpty(reg_mail.text.toString().trim()) -> {
                reg_mail.setError("Bitte E-Mail eintragen")
                return false
            }
            TextUtils.isEmpty(reg_pwd1.text.toString().trim()) -> {
                reg_pwd1.setError("Bitte Passwort eintragen")
                return false
            }
            TextUtils.isEmpty(reg_pwd2.text.toString().trim()) -> {
                reg_pwd2.setError("Bitte Passwort eintragen")
                return false
            }
            TextUtils.isEmpty(reg_name.text.toString().trim()) -> {
                reg_name.setError("Bitte Vornamen eintragen")
                return false
            }
            TextUtils.isEmpty(reg_name2.text.toString().trim()) -> {
                reg_name2.setError("Bitte Nachnamen eintragen")
                return false
            }
            reg_mail.text.toString().isNotEmpty() && reg_pwd1.toString()
                .isNotEmpty() && reg_pwd2.toString().isNotEmpty() -> {
                if (reg_mail.text.toString()
                        .matches(Regex("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"))
                ) {
                    if (reg_pwd1.text.toString() == reg_pwd2.text.toString()) {
                        //Passwörter stimmen überein
                    } else {
                        reg_pwd1.setError("Die Passwörter stimmen nicht überein")
                        reg_pwd2.setError("Die Passwörter stimmen nicht überein")
                        return false
                    }
                } else {
                    reg_mail.setError("Bitte gültige E-Mail eintragen")
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
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}