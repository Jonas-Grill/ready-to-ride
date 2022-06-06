package com.readytoride.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.readytoride.R


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var option : Spinner
    lateinit var result : TextView

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


        //Spinner
        var item: String
        item = ""
        val spinner = view.findViewById<Spinner>(R.id.dropdown_register)
        spinner?.adapter = activity?.let { ArrayAdapter.createFromResource(it, R.array.roles, android.R.layout.simple_spinner_item) } as SpinnerAdapter
        spinner?.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                item = parent?.getItemAtPosition(position).toString()

            }
        }



        if (item == "Trainer") {
            println(item)
            view.findViewById<Button>(R.id.btn_nxt).setOnClickListener {
                var navRegister = activity as FragmentNavigation
                navRegister.navigateFrag(TrainerRegisterFragment(), false)
            }
            println("end of")
        }
      else if(item == "Nutzer"){
            view.findViewById<Button>(R.id.btn_nxt).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(UserRegisterFragment(), false)
            }
        }
        else if(item == "Admin"){
        view.findViewById<Button>(R.id.btn_nxt).setOnClickListener {
            var navRegister = activity as FragmentNavigation
            navRegister.navigateFrag(AdminRegisterFragment(), false)
            }
        }
        return view
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
        // TODO: Rename and change types and number of parameters
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