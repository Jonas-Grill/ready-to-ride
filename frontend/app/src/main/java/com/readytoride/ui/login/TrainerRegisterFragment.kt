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
import androidx.drawerlayout.widget.DrawerLayout
import com.readytoride.MainActivity
import com.readytoride.R
import com.readytoride.ui.home.HomeFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TrainerRegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TrainerRegisterFragment : Fragment() {
    private lateinit var trainer_age: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_trainer_register, container, false)

        trainer_age = view.findViewById(R.id.trainer_age)

        view.findViewById<Button>(R.id.btn_finish).setOnClickListener {
            validateInput()
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(HomeFragment(), true)
        }
        return view
    }

    fun validateInput() {
        when {
            TextUtils.isEmpty(trainer_age.text.toString().trim()) -> {
                trainer_age.setError("Bitte Alter eintragen")
            }
            trainer_age.toString().isNotEmpty() -> {
                if (trainer_age.text.toString().matches(Regex("[0-9]"))) {
                    Toast.makeText(context, "Registrierung erfolgreich", Toast.LENGTH_SHORT).show()
                } else {
                    trainer_age.setError("Bitte gültige Zahl eintragen")
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
         * @return A new instance of fragment TrainerRegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TrainerRegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}