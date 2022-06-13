package com.readytoride.ui.stable

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.readytoride.R

class StableFragment : Fragment(){

    companion object {
        fun newInstance() = StableFragment()
    }

    private lateinit var viewModel: StableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_stable, container, false)

        //ToDo: Rollencheck macht Button sichtbar
       // if (rolle == Admin){//ToDo: Rollencheck
            view.findViewById<Button>(R.id.btn_edit_stable).visibility = View.VISIBLE
      //  }
        view.findViewById<Button>(R.id.btn_edit_stable).setOnClickListener{
            //ToDo: Felder bearbeitbar machen
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StableViewModel::class.java)
    }

}