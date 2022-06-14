package com.readytoride.ui.stable

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.ui.horse.HorseDatasource
import com.readytoride.ui.horse.HorseItemAdapter

class StableFragment : Fragment(){

    companion object {
        fun newInstance() = StableFragment()
    }

    private lateinit var viewModel: StableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stable, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StableViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDatasetBox = BoxDatasource().loadBoxes()
        val myDatasetArena = ArenaDatasource().loadArenas()

        val recyclerViewBox = view.findViewById<RecyclerView>(R.id.recycler_view_box)
        val recyclerViewArena = view.findViewById<RecyclerView>(R.id.recycler_view_arenas)

        recyclerViewBox.adapter = BoxItemAdapter(this, myDatasetBox)
        recyclerViewBox.setHasFixedSize(true)

        recyclerViewArena.adapter = ArenaItemAdapter(this, myDatasetArena)
        recyclerViewArena.setHasFixedSize(true)
    }

}