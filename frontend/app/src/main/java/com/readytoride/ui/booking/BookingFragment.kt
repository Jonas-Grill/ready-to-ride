package com.readytoride.ui.booking

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.LessonEntity
import com.readytoride.network.StableApi.StableEntity
import com.readytoride.network.UserApi.UserEntity
import java.time.LocalDate

class BookingFragment : Fragment() {

    companion object {
        fun newInstance() = BookingFragment()
    }

    var horseId = ""
    var trainerId = ""
    val args: BookingFragmentArgs by navArgs<BookingFragmentArgs>()

    private lateinit var viewModel: BookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trainerId = args.trainerid.toString()
        horseId = args.horseid.toString()
        viewModel = ViewModelProvider(this).get(BookingViewModel::class.java)
        return inflater.inflate(R.layout.fragment_booking, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val role: String? = sharedPref?.getString("role", "defaultRole")
        val token: String? = sharedPref?.getString("token", "DefaultValue")
        viewModel.role = role

        val bookLesson: ScrollView = view.findViewById(R.id.booking_content)
        val createLesson: LinearLayout = view.findViewById(R.id.create_lesson_content)

        if (role != "Trainer"){
            createLesson.visibility = View.GONE
        } else {
            bookLesson.visibility = View.GONE
        }

        val timeframe: TextView = view.findViewById(R.id.text_timespan)
        val textDay1: TextView = view.findViewById(R.id.heading_day1)
        val textDay2: TextView = view.findViewById(R.id.heading_day2)
        val textDay3: TextView = view.findViewById(R.id.heading_day3)
        val textDay4: TextView = view.findViewById(R.id.heading_day4)
        val textDay5: TextView = view.findViewById(R.id.heading_day5)
        val textDay6: TextView = view.findViewById(R.id.heading_day6)
        val textDay7: TextView = view.findViewById(R.id.heading_day7)
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

        viewModel.getAllHorses()
        val myHorseObserver = Observer<MutableList<HorseEntity>> { newHorseList -> run{
            val recyclerViewHorse = view.findViewById<RecyclerView>(R.id.recycler_view_selection_horse)
            recyclerViewHorse.adapter = BookingHorseItemAdapter(this, newHorseList, horseId)
            recyclerViewHorse.setHasFixedSize(true)
        }}
        viewModel.horses.observe(viewLifecycleOwner, myHorseObserver)

        viewModel.getAllTrainer()
        val myTrainerObserver = Observer<MutableList<UserEntity>> { newTrainerList -> run{
            val recyclerViewTrainer = view.findViewById<RecyclerView>(R.id.recycler_view_selection_trainer)
            recyclerViewTrainer.adapter = BookingTrainerItemAdapter(this, newTrainerList, trainerId)
            recyclerViewTrainer.setHasFixedSize(true)
        }}
        viewModel.trainer.observe(viewLifecycleOwner, myTrainerObserver)

        var date1 = LocalDate.now()
        var date2 = LocalDate.now().plusDays(1)
        var date3 = LocalDate.now().plusDays(2)
        var date4 = LocalDate.now().plusDays(3)
        var date5 = LocalDate.now().plusDays(4)
        var date6 = LocalDate.now().plusDays(5)
        var date7 = LocalDate.now().plusDays(6)

        viewModel.getAllLessons(date1.toString(), date7.toString())
        val myLessonsObserverBook = Observer<MutableList<LessonEntity>> { newLessonsList -> run{

            timeframe.text = date1.dayOfMonth.toString() + "." + date1.monthValue + "." + date1.year + " - " + date7.dayOfMonth + "." + date7.monthValue + "." + date7.year

            textDay1.text = date1.dayOfMonth.toString() + "." + date1.monthValue + "." + date1.year
            textDay2.text = date2.dayOfMonth.toString() + "." + date2.monthValue + "." + date2.year
            textDay3.text = date3.dayOfMonth.toString() + "." + date3.monthValue + "." + date3.year
            textDay4.text = date4.dayOfMonth.toString() + "." + date4.monthValue + "." + date4.year
            textDay5.text = date5.dayOfMonth.toString() + "." + date5.monthValue + "." + date5.year
            textDay6.text = date6.dayOfMonth.toString() + "." + date6.monthValue + "." + date6.year
            textDay7.text = date7.dayOfMonth.toString() + "." + date7.monthValue + "." + date7.year

            val lessonsDay1 = newLessonsList.filter { it.day == date1.toString()}
            val recyclerViewBookingsMonday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day1)
            recyclerViewBookingsMonday.adapter = BookingSlotItemAdapter(this, lessonsDay1)
            recyclerViewBookingsMonday.setHasFixedSize(true)

            val lessonsDay2 = newLessonsList.filter { it.day == date2.toString()}
            val recyclerViewBookingsTuesday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day2)
            recyclerViewBookingsTuesday.adapter = BookingSlotItemAdapter(this, lessonsDay2)
            recyclerViewBookingsTuesday.setHasFixedSize(true)

            val lessonsDay3 = newLessonsList.filter { it.day == date3.toString()}
            val recyclerViewBookingsWednesday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day3)
            recyclerViewBookingsWednesday.adapter =
                BookingSlotItemAdapter(this, lessonsDay3)
            recyclerViewBookingsWednesday.setHasFixedSize(true)

            val lessonsDay4 = newLessonsList.filter { it.day == date4.toString()}
            val recyclerViewBookingsThursday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day4)
            recyclerViewBookingsThursday.adapter =
                BookingSlotItemAdapter(this, lessonsDay4)
            recyclerViewBookingsThursday.setHasFixedSize(true)

            val lessonsDay5 = newLessonsList.filter { it.day == date5.toString()}
            val recyclerViewBookingsFriday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day5)
            recyclerViewBookingsFriday.adapter = BookingSlotItemAdapter(this, lessonsDay5)
            recyclerViewBookingsFriday.setHasFixedSize(true)

            val lessonsDay6 = newLessonsList.filter { it.day == date6.toString()}
            val recyclerViewBookingsSaturday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day6)
            recyclerViewBookingsSaturday.adapter =
                BookingSlotItemAdapter(this, lessonsDay6)
            recyclerViewBookingsSaturday.setHasFixedSize(true)

            val lessonsDay7 = newLessonsList.filter { it.day == date7.toString()}
            val recyclerViewBookingsSunday =
                view.findViewById<RecyclerView>(R.id.recycler_view_bookings_day7)
            recyclerViewBookingsSunday.adapter = BookingSlotItemAdapter(this, lessonsDay7)
            recyclerViewBookingsSunday.setHasFixedSize(true)
        }}
        viewModel.lessons.observe(viewLifecycleOwner, myLessonsObserverBook)

        val expandedView: LinearLayout = view.findViewById(R.id.expanded_view)
        expandedView.visibility = View.VISIBLE

        val buttonExpand: Button = view.findViewById(R.id.button6)
        buttonExpand.setOnClickListener {
            expandedView.visibility = if (expandedView.visibility == View.VISIBLE) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        val buttonNext: Button = view.findViewById(R.id.button3)
        val buttonBefore: Button = view.findViewById(R.id.button2)

        if (date1 == LocalDate.now()) {
            buttonBefore.visibility = View.INVISIBLE
        }

        buttonNext.setOnClickListener {
            date1 = date1.plusDays(7)
            date2 = date1.plusDays(1)
            date3 = date1.plusDays(2)
            date4 = date1.plusDays(3)
            date5 = date1.plusDays(4)
            date6 = date1.plusDays(5)
            date7 = date1.plusDays(6)

            buttonBefore.visibility = View.VISIBLE
            viewModel.getAllLessons(date1.toString(), date7.toString())
        }

        buttonBefore.setOnClickListener {
            date1 = date1.minusDays(7)
            date2 = date1.plusDays(1)
            date3 = date1.plusDays(2)
            date4 = date1.plusDays(3)
            date5 = date1.plusDays(4)
            date6 = date1.plusDays(5)
            date7 = date1.plusDays(6)

            if (date1 == LocalDate.now()){
                buttonBefore.visibility = View.INVISIBLE
            }

            viewModel.getAllLessons(date1.toString(), date7.toString())
        }

        val buttonSubmit: Button = view.findViewById(R.id.button4)
        buttonSubmit.setOnClickListener {
            expandedView.visibility = View.GONE

            viewModel.getAllLessons(date1.toString(), date7.toString())
        }

        /////////////////////// Create Lesson ////////////////////////////

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
            viewModel.getAllLessonsWithoutDate()
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
                    viewModel.getAllLessonsWithoutDate()
                }
            }
        }}
        viewModel.stable.observe(viewLifecycleOwner, myLocationObserver)

        viewModel.getAllLessonsWithoutDate()
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

        button.setOnClickListener {
            viewModel.createLessons(token)
        }

    }

}