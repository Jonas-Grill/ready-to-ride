package com.readytoride.ui.trainer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDataset = TrainerDatasource().loadTrainer()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_trainer)
        recyclerView.adapter = TrainerItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)
    }
}