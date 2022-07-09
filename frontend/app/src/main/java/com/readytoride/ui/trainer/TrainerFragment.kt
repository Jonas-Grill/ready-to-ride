package com.readytoride.ui.trainer

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.UserApi.UserEntity

class TrainerFragment : Fragment() {

    companion object {
        fun newInstance() = TrainerFragment()
    }

    private lateinit var viewModel: TrainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TrainerViewModel::class.java)
        return inflater.inflate(R.layout.fragment_trainer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllTrainer()
        val myObserver = Observer<MutableList<UserEntity>> { newList -> run{
            val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_trainer)
            recyclerView.adapter = TrainerItemAdapter(this, newList)
            recyclerView.setHasFixedSize(true)
        }}
        viewModel.trainer.observe(viewLifecycleOwner, myObserver)
    }
}