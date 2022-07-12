package com.readytoride.ui.editarenas

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import com.google.android.material.textfield.TextInputEditText
import com.readytoride.R
import com.readytoride.network.StableApi.Arena
import com.readytoride.network.StableApi.Box
import com.readytoride.network.StableApi.StableEntity

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

        val spinnerAction: Spinner = view.findViewById(R.id.spinner_action_arena)
        val spinnerArenaEdit: Spinner = view.findViewById(R.id.spinner_arena_edit)
        val spinnerArenaDelete: Spinner = view.findViewById(R.id.spinner_arena_delete)

        var stableObject: StableEntity = StableEntity("", "", "", listOf<Arena>(), listOf<Box>())
        var arenas: MutableList<Arena> = mutableListOf()
        var selectedArenaEdit = ""
        var selectedArenaDelete = ""

        layoutAdd.visibility = View.GONE
        layoutEdit.visibility = View.GONE
        layoutDelete.visibility = View.GONE

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "DefaultValue")

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable ->
            run {
                val myDatasetArena = newStable.arenas
                arenas = newStable.arenas.toMutableList()
                stableObject = newStable

                val myArenaNames: MutableList<String> = mutableListOf()
                for (item in myDatasetArena) {
                    myArenaNames.add(item.name)
                }

                ArrayAdapter.createFromResource(
                    view.context, R.array.actionsarena, android.R.layout.simple_spinner_item
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerAction.adapter = adapter
                }
                spinnerAction.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {

                    }

                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        if (id == 0.toLong()) {
                            layoutAdd.visibility = View.GONE
                            layoutEdit.visibility = View.VISIBLE
                            layoutDelete.visibility = View.GONE
                        } else if (id == 1.toLong()) {
                            layoutAdd.visibility = View.VISIBLE
                            layoutEdit.visibility = View.GONE
                            layoutDelete.visibility = View.GONE
                        } else if (id == 2.toLong()) {
                            layoutAdd.visibility = View.GONE
                            layoutEdit.visibility = View.GONE
                            layoutDelete.visibility = View.VISIBLE
                        }
                    }
                }

                ArrayAdapter(
                    view.context, android.R.layout.simple_spinner_item, myArenaNames
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    // Apply the adapter to the spinner
                    spinnerArenaDelete.adapter = adapter
                }
                spinnerArenaDelete.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedArenaDelete = spinnerArenaDelete.selectedItem.toString()
                        }
                    }

                ArrayAdapter(
                    view.context, android.R.layout.simple_spinner_item, myArenaNames
                ).also { adapter ->
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinnerArenaEdit.adapter = adapter
                }
                spinnerArenaEdit.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onNothingSelected(parent: AdapterView<*>?) {
                        }

                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            selectedArenaEdit = spinnerArenaEdit.selectedItem.toString()
                            for (arena in newStable.arenas) {
                                if (arena.name == selectedArenaEdit) {
                                    changeName.setText(arena.name)
                                    changeSize.setText(arena.size.toString())
                                }
                            }
                        }
                    }
            }
        }
        viewModel.stable.observe(viewLifecycleOwner, myObserver)

        val buttonSubmitChanges: Button = view.findViewById(R.id.button10)
        buttonSubmitChanges.setOnClickListener {
            if (spinnerAction.selectedItem.toString() == "Anlage bearbeiten") {
                for (arena in arenas) {
                    if (arena.name == selectedArenaEdit) {
                        arenas.remove(arena)
                        arenas.add(
                            Arena(
                                changeName.text.toString(),
                                changeSize.text.toString().toInt()
                            )
                        )
                        break
                    }
                }
            } else if (spinnerAction.selectedItem.toString() == "Anlage erstellen") {
                arenas.add(Arena(addName.text.toString(), addSize.text.toString().toInt()))
                addName.text?.clear()
                addSize.text?.clear()
            } else if (spinnerAction.selectedItem.toString() == "Anlage löschen") {
                for (arena in arenas) {
                    if (arena.name == selectedArenaDelete) {
                        arenas.remove(arena)
                        break
                    }
                }
            }

            stableObject.arenas = arenas
            viewModel.setStable(token, stableObject)
        }
    }
}