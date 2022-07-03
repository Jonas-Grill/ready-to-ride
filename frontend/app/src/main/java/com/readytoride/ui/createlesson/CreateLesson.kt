package com.readytoride.ui.createlesson

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.LessonEntity
import com.readytoride.network.StableApi.Arena
import com.readytoride.network.StableApi.StableEntity
import com.readytoride.ui.horse.HorseItemAdapter

class CreateLesson : Fragment() {

    companion object {
        fun newInstance() = CreateLesson()
    }

    private lateinit var viewModel: CreateLessonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(CreateLessonViewModel::class.java)
        return inflater.inflate(R.layout.fragment_create_lesson, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val date: CalendarView = view.findViewById(R.id.calendarView)
        val time: LinearLayout = view.findViewById(R.id.layout_time)
        val location: LinearLayout = view.findViewById(R.id.layout_choose_arena)
        val button: Button = view.findViewById(R.id.button11)
        val spinnerArena: Spinner = view.findViewById(R.id.spinner)
        val spinnerFrom: Spinner = view.findViewById(R.id.spinner_from)
        val labenSpinnerTo: TextView = view.findViewById(R.id.textView27)
        val spinnerTo: Spinner = view.findViewById(R.id.spinner_to)

        val timeslotsFrom: MutableList<String> = mutableListOf()
        val timeslotsTo: MutableList<String> = mutableListOf()
        var arenas: MutableList<String> = mutableListOf()
        var bookedLessons: MutableList<LessonEntity> =  mutableListOf()

        for (i in 8..20){
            timeslotsFrom.add(i.toString() + ":00 Uhr")
        }

        for (i in 9..21){
            timeslotsTo.add(i.toString() + ":00 Uhr")
        }

        date.setOnDateChangeListener { view, year, month, dayOfMonth ->  run {
            val selectedDate = year.toString()+"-"+(month+1)+"-"+dayOfMonth
            viewModel.setDate(selectedDate)
            viewModel.getAllLessons()
        }}

        spinnerTo.visibility = View.INVISIBLE
        labenSpinnerTo.visibility = View.INVISIBLE

        viewModel.getStable()
        val myLocationObserver = Observer<StableEntity> { newStable -> run{
            for (arena in newStable.arenas) {
                arenas.add(arena.name)
            }

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
                    viewModel.setArena(spinnerArena.selectedItem.toString())
                    viewModel.getAllLessons()
                }
            }
        }}
        viewModel.stable.observe(viewLifecycleOwner, myLocationObserver)

        viewModel.getAllLessons()
        val myLessonsObserver = Observer<MutableList<LessonEntity>> {  newLessons -> run {
            bookedLessons = newLessons.filter { it.arena == viewModel.selectedArena }.toMutableList()
        }
            for (lesson in bookedLessons){
                var start = lesson.startHour.toString() + ":00 Uhr"
                if (start in timeslotsFrom){
                    timeslotsFrom.remove(start)
                }
            }

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
                    spinnerTo.visibility = View.VISIBLE
                    labenSpinnerTo.visibility = View.VISIBLE
                    var selectedTime = spinnerFrom.selectedItem.toString()
                    var selectedList = selectedTime.split(":")
                    var selectedInt = selectedList[0].toInt()
                    viewModel.setTimeFrom(selectedInt)
                    viewModel.setTimeTo(selectedInt+1)
                    for (time in timeslotsTo){
                        var timeList = time.split(":")
                        var timeInt = timeList[0].toInt()
                        if (timeInt <= selectedInt){
                            timeslotsFrom.remove(time)
                        }
                    }
                    for (lesson in bookedLessons){
                        if (selectedInt < lesson.startHour) {
                            for (time in timeslotsTo){
                                var timeList2 = time.split(":")
                                var timeInt2 = timeList2[0].toInt()
                                if (timeInt2 > lesson.startHour){
                                    timeslotsFrom.remove(time)
                                }
                            }
                        }
                    }
                }
            }

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
                    var selectedTime = spinnerTo.selectedItem.toString()
                    var selectedList = selectedTime.split(":")
                    var selectedInt = selectedList[0].toInt()
                    viewModel.setTimeTo(selectedInt)
                }
            }
        }
        viewModel.lessons.observe(viewLifecycleOwner, myLessonsObserver)
    }
}