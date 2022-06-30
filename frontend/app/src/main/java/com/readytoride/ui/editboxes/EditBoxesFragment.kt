package com.readytoride.ui.editboxes

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

class EditBoxesFragment : Fragment() {

    companion object {
        fun newInstance() = EditBoxesFragment()
    }

    private lateinit var viewModel: EditBoxesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_boxes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EditBoxesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val changeName: TextInputEditText = view.findViewById(R.id.edit_box_name)
        val changeSize: TextInputEditText = view.findViewById(R.id.edit_box_size)
        val changePrice: TextInputEditText = view.findViewById(R.id.edit_box_price)
        val changeNumber: TextInputEditText = view.findViewById(R.id.edit_box_number)
        val addName: TextInputEditText = view.findViewById(R.id.add_box_name)
        val addSize: TextInputEditText = view.findViewById(R.id.add_box_size)
        val addPrice: TextInputEditText = view.findViewById(R.id.add_box_price)
        val addNumber: TextInputEditText = view.findViewById(R.id.add_box_number)
        val layoutEdit: LinearLayout = view.findViewById(R.id.action_edit_box)
        val layoutAdd: LinearLayout = view.findViewById(R.id.action_add_boxes)
        val layoutDelete: LinearLayout = view.findViewById(R.id.action_delete_box)

        layoutAdd.visibility = View.GONE
        layoutEdit.visibility = View.GONE
        layoutDelete.visibility = View.GONE

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable -> run{
            val myDatasetBox = newStable.boxes
            val recyclerBox: RecyclerView = view.findViewById(R.id.recycler_delete_boxes)
            recyclerBox.adapter = BoxesSelectionItemAdapter(this, myDatasetBox)
            recyclerBox.setHasFixedSize(true)

            val spinnerAction: Spinner = view.findViewById(R.id.spinner_action_box)
            ArrayAdapter.createFromResource(view.context, R.array.actionsbox, android.R.layout.simple_spinner_item
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

            val myBoxNames: MutableList<String> = mutableListOf()
            for (item in myDatasetBox){
                myBoxNames.add(item.name)
            }
            val spinnerArena: Spinner = view.findViewById(R.id.spinner_box)
            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, myBoxNames
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

        val buttonSubmitChanges: Button = view.findViewById(R.id.buttonSubmitChangesBox)
        buttonSubmitChanges.setOnClickListener {
            //TODO: Read Changes and send them to backend
        }
    }
}