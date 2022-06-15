package com.readytoride.ui.createlesson

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.readytoride.R

class CreateLesson : Fragment() {

    companion object {
        fun newInstance() = CreateLesson()
    }

    private lateinit var viewModel: CreateLessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_lesson, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateLessonViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date: CalendarView = view.findViewById(R.id.calendarView)
        val time: LinearLayout = view.findViewById(R.id.layout_time)
        val location: LinearLayout = view.findViewById(R.id.layout_choose_arena)
        val button: Button = view.findViewById(R.id.button11)

        val timeslotsFrom: List<String> = listOf("11:00 Uhr", "13:00 Uhr", "14:00 Uhr", "15:00 Uhr", "19:00 Uhr")
        val timeslotsTo: List<String> = listOf("12:00 Uhr", "14:00 Uhr", "15:00 Uhr", "16:00 Uhr", "20:00 Uhr")
        val arenas: List<String> = listOf("Halle 1", "Reitplatz 2", "Roundpen")

        val spinnerFrom: Spinner = view.findViewById(R.id.spinner_from)
        ArrayAdapter(view.context, android.R.layout.simple_spinner_item, timeslotsFrom
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerFrom.adapter = adapter
        }
        spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO
            }
        }

        val spinnerTo: Spinner = view.findViewById(R.id.spinner_to)
        ArrayAdapter(view.context, android.R.layout.simple_spinner_item, timeslotsTo
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerTo.adapter = adapter
        }
        spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO

            }
        }

        val spinnerArena: Spinner = view.findViewById(R.id.spinner)
        ArrayAdapter(view.context, android.R.layout.simple_spinner_item, arenas
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinnerArena.adapter = adapter
        }
        spinnerArena.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //TODO

            }
        }
    }

}