package com.readytoride.ui.stable

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.StableApi.StableEntity

class StableFragment : Fragment(){

    companion object {
        fun newInstance() = StableFragment()
    }

    private lateinit var viewModel: StableViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(StableViewModel::class.java)
        return inflater.inflate(R.layout.fragment_stable, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewBox = view.findViewById<RecyclerView>(R.id.recycler_view_box)
        val recyclerViewArena = view.findViewById<RecyclerView>(R.id.recycler_view_arenas)

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable -> run{
            val myDatasetArena = newStable.arenas
            val myDatasetBox = newStable.boxes
            recyclerViewBox.adapter = BoxItemAdapter(this, myDatasetBox)
            recyclerViewBox.setHasFixedSize(true)

            recyclerViewArena.adapter = ArenaItemAdapter(this, myDatasetArena)
            recyclerViewArena.setHasFixedSize(true)
        }}
        viewModel.stable.observe(viewLifecycleOwner, myObserver)

        val buttonEditBoxes = view.findViewById<Button>(R.id.button8)
        val buttonEditArenas = view.findViewById<Button>(R.id.button9)

        buttonEditBoxes.setOnClickListener{
            val action = StableFragmentDirections.actionNavStableToEditBoxesFragment()
            view.findNavController().navigate(action)
        }

        buttonEditArenas.setOnClickListener{
            val action = StableFragmentDirections.actionNavStableToEditArenasFragment()
            view.findNavController().navigate(action)
        }

        //TODO: Buttons only visible for Admins

    }

}