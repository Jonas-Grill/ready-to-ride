package com.readytoride.ui.horse

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity

class HorseFragment : Fragment() {

    companion object {
        fun newInstance() = HorseFragment()
    }

    private lateinit var viewModel: HorseViewModel
    private lateinit var horseList: List<HorseEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_horse, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HorseViewModel::class.java)
        viewModel.getHorseList()
        horseList = viewModel.horses
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val myDataset = HorseDatasource().loadHorses()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_horse)
        recyclerView.adapter = HorseItemAdapter(this, this.horseList)
        recyclerView.setHasFixedSize(true)
    }
}