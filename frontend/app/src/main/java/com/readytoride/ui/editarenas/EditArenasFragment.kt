package com.readytoride.ui.editarenas

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.readytoride.R
import com.readytoride.network.StableApi.StableEntity
import com.readytoride.ui.stable.ArenaItemAdapter
import com.readytoride.ui.stable.BoxItemAdapter

class EditArenasFragment : Fragment() {

    companion object {
        fun newInstance() = EditArenasFragment()
    }

    private lateinit var viewModel: EditArenasViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EditArenasViewModel::class.java)
        return inflater.inflate(R.layout.fragment_edit_arenas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeName: TextInputEditText = view.findViewById(R.id.edit_arena_name)
        val changeSize: TextInputEditText = view.findViewById(R.id.edit_arena_size)
        val addName: TextInputEditText = view.findViewById(R.id.add_arena_name)
        val addSize: TextInputEditText = view.findViewById(R.id.add_arena_size)
        val layoutEdit: LinearLayout = view.findViewById(R.id.action_edit_arena)
        val layoutAdd: LinearLayout = view.findViewById(R.id.action_add_arena)
        val layoutDelete: LinearLayout = view.findViewById(R.id.action_delete_arena)

        layoutAdd.visibility = View.GONE
        layoutEdit.visibility = View.GONE
        layoutDelete.visibility = View.GONE

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable -> run{
            val myDatasetArena = newStable.arenas
            val recyclerArenas: RecyclerView = view.findViewById(R.id.recycler_delete_arenas)
            recyclerArenas.adapter = ArenaSelectionItemAdapter(this, myDatasetArena)
            recyclerArenas.setHasFixedSize(true)

            val spinnerAction: Spinner = view.findViewById(R.id.spinner_action_arena)
            ArrayAdapter.createFromResource(view.context, R.array.actionsarena, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerAction.adapter = adapter
            }
            spinnerAction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (id == 0.toLong()){
                        layoutAdd.visibility = View.GONE
                        layoutEdit.visibility = View.VISIBLE
                        layoutDelete.visibility = View.GONE
                    } else if (id == 1.toLong()){
                        layoutAdd.visibility = View.VISIBLE
                        layoutEdit.visibility = View.GONE
                        layoutDelete.visibility = View.GONE
                    } else if (id == 2.toLong()){
                        layoutAdd.visibility = View.GONE
                        layoutEdit.visibility = View.GONE
                        layoutDelete.visibility = View.VISIBLE
                    }
                }
            }

            val myArenaNames: MutableList<String> = mutableListOf()
            for (item in myDatasetArena){
                myArenaNames.add(item.name)
            }
            val spinnerArena: Spinner = view.findViewById(R.id.spinner_arena)
            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, myArenaNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner
                spinnerArena.adapter = adapter
            }
            spinnerArena.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    //TODO: Change Text in TextInput as it is now

                }
            }
        }}
        viewModel.stable.observe(viewLifecycleOwner, myObserver)

        val buttonSubmitChanges: Button = view.findViewById(R.id.button10)
        buttonSubmitChanges.setOnClickListener {
            //TODO: Read Changes and send them to backend
        }
    }
}