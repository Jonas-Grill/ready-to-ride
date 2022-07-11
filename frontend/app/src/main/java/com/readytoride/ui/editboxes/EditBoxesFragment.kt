package com.readytoride.ui.editboxes

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

class EditBoxesFragment : Fragment() {

    companion object {
        fun newInstance() = EditBoxesFragment()
    }

    private lateinit var viewModel: EditBoxesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(EditBoxesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_edit_boxes, container, false)
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

        val spinnerAction: Spinner = view.findViewById(R.id.spinner_action_box)
        val spinnerBoxEdit: Spinner = view.findViewById(R.id.spinner_box_edit)
        val spinnerBoxDelete: Spinner = view.findViewById(R.id.spinner_boxes_delete)

        var stableObject: StableEntity = StableEntity("", "", "", listOf<Arena>(), listOf<Box>())
        var boxes: MutableList<Box> = mutableListOf()
        var selectedBoxEdit = ""
        var selectedBoxDelete = ""

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "DefaultValue")

        layoutAdd.visibility = View.GONE
        layoutEdit.visibility = View.GONE
        layoutDelete.visibility = View.GONE

        viewModel.getStable()
        val myObserver = Observer<StableEntity> { newStable -> run{
            val myDatasetBox = newStable.boxes
            boxes = newStable.boxes.toMutableList()
            stableObject = newStable

            val myBoxNames: MutableList<String> = mutableListOf()
            for (item in myDatasetBox){
                myBoxNames.add(item.name)
            }

            ArrayAdapter.createFromResource(view.context, R.array.actionsbox, android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
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

            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, myBoxNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerBoxDelete.adapter = adapter
            }
            spinnerBoxDelete.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedBoxDelete = spinnerBoxDelete.selectedItem.toString()
                }
            }

            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, myBoxNames
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerBoxEdit.adapter = adapter
            }
            spinnerBoxEdit.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    selectedBoxEdit = spinnerBoxEdit.selectedItem.toString()
                    for (box in newStable.boxes){
                        if (box.name == selectedBoxEdit) {
                            changeName.setText(box.name)
                            changeSize.setText(box.size.toString())
                            changeNumber.setText(box.count.toString())
                            changePrice.setText(box.price.toString())
                        }
                    }

                }
            }
        }}
        viewModel.stable.observe(viewLifecycleOwner, myObserver)

        val buttonSubmitChanges: Button = view.findViewById(R.id.buttonSubmitChangesBox)
        buttonSubmitChanges.setOnClickListener {
            if (spinnerAction.selectedItem.toString() == "Box bearbeiten") {
                for (box in boxes) {
                    if (box.name == selectedBoxEdit) {
                        boxes.remove(box)
                        boxes.add(
                            Box(
                                changeName.text.toString(),
                                changePrice.text.toString().toInt(),
                                changeSize.text.toString().toDouble(),
                                changeNumber.text.toString().toInt()
                            )
                        )
                        break
                    }
                }
            } else if (spinnerAction.selectedItem.toString() == "Box erstellen") {
                boxes.add(Box(addName.text.toString(), addPrice.text.toString().toInt(), addSize.text.toString().toDouble(), addNumber.text.toString().toInt()))
                addName.text?.clear()
                addSize.text?.clear()
                addPrice.text?.clear()
                addNumber.text?.clear()
            } else if (spinnerAction.selectedItem.toString() == "Box löschen") {
                for (box in boxes) {
                    if (box.name == selectedBoxDelete) {
                        boxes.remove(box)
                        break
                    }
                }
            }

            stableObject.boxes = boxes
            viewModel.setStable(token, stableObject)
        }
    }
}