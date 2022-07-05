package com.readytoride.ui.createlesson

import android.content.Context
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
import java.util.Locale.filter

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
        var extraTimeSlots: MutableList<String> = mutableListOf()
        var arenas: MutableList<String> = mutableListOf()
        var bookedLessons: MutableList<LessonEntity> =  mutableListOf()
        var selectedDate: String = ""

        for (i in 0..23){
            timeslotsFrom.add(i.toString() + ":00 Uhr")
        }

        for (i in 1..24){
            timeslotsTo.add(i.toString() + ":00 Uhr")
        }

        date.setOnDateChangeListener { view, year, month, dayOfMonth ->  run {
            var selectedDay: String = ""
            var selectedMonth: String = ""
            if (dayOfMonth < 10) {
                selectedDay = "0" + dayOfMonth
            } else {
                selectedDay = dayOfMonth.toString()
            }
            if (month < 10) {
                selectedMonth = "0" + (month+1)
            } else {
                selectedMonth = (month+1).toString()
            }
            selectedDate = year.toString()+"-"+selectedMonth+"-"+selectedDay
            viewModel.setDate(selectedDate)
            viewModel.getAllLessons()
        }}

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
            bookedLessons = newLessons.filter { it.day == selectedDate }
                .filter { it.arena == spinnerArena.selectedItem.toString()}.toMutableList()

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
                        if (timeInt > selectedInt){
                            extraTimeSlots.add(time)
                        }
                    }
                    viewModel.getAllLessons2()
                }
            }
        }}
        viewModel.lessons.observe(viewLifecycleOwner, myLessonsObserver)

        viewModel.getAllLessons2()
        val myLessonsObserver2 = Observer<MutableList<LessonEntity>> {  newLessons -> run {

            val timeComparator = Comparator<LessonEntity> { o1, o2 -> o1.startHour - o2.startHour }
            bookedLessons = newLessons.filter { it.day == selectedDate }
                .filter { it.arena == spinnerArena.selectedItem.toString()}
                .sortedWith(timeComparator).toMutableList()

            val selectedTime = spinnerFrom.selectedItem.toString()
            val selectedList = selectedTime.split(":")
            val selectedInt = selectedList[0].toInt()

            val removalList: MutableList<String> = mutableListOf()

            extraTimeSlots.clear()
            for (i in (selectedInt+1)..24) {
                extraTimeSlots.add(i.toString() + ":00 Uhr")
            }

            for (lesson in bookedLessons) {
                if (lesson.startHour > selectedInt) {
                    val begin = lesson.startHour + 1
                    for (j in begin..24){
                        removalList.add(j.toString() + ":00 Uhr")
                    }
                    extraTimeSlots.removeAll(removalList)
                    break
                }
            }

            ArrayAdapter(view.context, android.R.layout.simple_spinner_item, extraTimeSlots
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

        }}
        viewModel.lessons2.observe(viewLifecycleOwner, myLessonsObserver2)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String? = sharedPref?.getString("token", "DefaultValue")

        button.setOnClickListener {
            viewModel.createLessons(token)
        }
    }
}