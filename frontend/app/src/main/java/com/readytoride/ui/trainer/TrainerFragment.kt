package com.readytoride.ui.trainer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.readytoride.R

class TrainerFragment : Fragment() {

    companion object {
        fun newInstance() = TrainerFragment()
    }

    private lateinit var viewModel: TrainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_trainer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrainerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}