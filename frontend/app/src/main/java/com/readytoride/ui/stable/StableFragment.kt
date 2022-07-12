package com.readytoride.ui.stable

import android.content.Context
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

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable -> run{
            val myDatasetArena = newStable.arenas
            val myDatasetBox = newStable.boxes
            val recyclerViewBox = view.findViewById<RecyclerView>(R.id.recycler_view_box)
            val recyclerViewArena = view.findViewById<RecyclerView>(R.id.recycler_view_arenas)

            recyclerViewBox.adapter = BoxItemAdapter(this, myDatasetBox)
            recyclerViewBox.setHasFixedSize(true)

            recyclerViewArena.adapter = ArenaItemAdapter(this, myDatasetArena)
            recyclerViewArena.setHasFixedSize(true)

        }}
        viewModel.stable.observe(viewLifecycleOwner, myObserver)

        val buttonEditBoxes = view.findViewById<Button>(R.id.button8)
        val buttonEditArenas = view.findViewById<Button>(R.id.button9)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val role: String? = sharedPref?.getString("role", "defaultRole")
        if (role != "Admin") {
            buttonEditBoxes.visibility = View.GONE
            buttonEditArenas.visibility = View.GONE
        }

        buttonEditBoxes.setOnClickListener{
            val action = StableFragmentDirections.actionNavStableToEditBoxesFragment()
            view.findNavController().navigate(action)
        }

        buttonEditArenas.setOnClickListener{
            val action = StableFragmentDirections.actionNavStableToEditArenasFragment()
            view.findNavController().navigate(action)
        }

    }

}