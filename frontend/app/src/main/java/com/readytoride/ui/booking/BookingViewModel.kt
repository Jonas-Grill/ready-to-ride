package com.readytoride.ui.booking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.*
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class BookingViewModel : ViewModel() {
    private val selectedTrainer: MutableList<String> = mutableListOf()
    private val selectedHorses: MutableList<String> = mutableListOf("")
    private var lessonsId: String = ""
    private var horseId: HorseIdForLesson = HorseIdForLesson("")
    var selectedDate:String = ""
    var selectedTimeFrom: Int = 0
    var selectedTimeTo: Int = 0
    var selectedArena: String = ""

    private val _horses = MutableLiveData<MutableList<HorseEntity>>()
    private val _trainer = MutableLiveData<MutableList<UserEntity>>()
    private val _lessons = MutableLiveData<MutableList<LessonEntity>>()
    private val _text = MutableLiveData<String>()
    private val _lessons2 = MutableLiveData<MutableList<LessonEntity>>()
    private val _stable = MutableLiveData<StableEntity>()
    val stable: LiveData<StableEntity> = _stable
    val lessons2: LiveData<MutableList<LessonEntity>> = _lessons2
    val horses: LiveData<MutableList<HorseEntity>> = _horses
    val trainer: LiveData<MutableList<UserEntity>> = _trainer
    val lessons: LiveData<MutableList<LessonEntity>> = _lessons
    val text: LiveData<String> = _text
    var role: String? = ""

    internal fun getAllHorses() {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorses().toMutableList()
                _horses.value = listResult
                println(listResult)
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
                println(e)
            }
        }
    }

    internal fun getAllTrainer() {
        viewModelScope.launch {
            try {
                val listResult = UserApi.retrofitService.getUsers().toMutableList()
                _trainer.value = listResult
            } catch (e: Exception){

            }
        }
    }

    internal fun getAllLessons(dateFrom: String, dateTo: String) {
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.getLessons("", selectedHorses, dateFrom, dateTo,true, false)
                _lessons.value = listResult
                println(listResult)
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    internal fun getAllLessonsWithoutDate() {
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.getLessons("",
                    mutableListOf(), selectedDate, null,false, null)
                _lessons.value = listResult
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    internal fun setBookingData(bookLesson: String, bookHorse: String) {
        lessonsId = bookLesson
        horseId = HorseIdForLesson(bookHorse)
    }

    internal fun bookLesson () {
        val token = "" //TODO: Get Token
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.makeBookingRequest(token, horseId, lessonsId)
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    fun getSelectedTrainers(): MutableList<String> {
        return selectedTrainer
    }

    fun addTrainer(trainer: String) {
        selectedTrainer.add(trainer)
    }

    fun removeTrainer(trainer: String) {
        selectedTrainer.remove(trainer)
    }

    fun isInTrainers(trainer: String): Boolean {
        return selectedTrainer.contains(trainer)
    }

    fun getSelectedHorses(): MutableList<String> {
        return selectedHorses
    }

    fun addHorse(horse: String) {
        selectedHorses.add(horse)
    }

    fun removeHorse(horse: String) {
        selectedHorses.remove(horse)
    }

    fun isInHorses(horse: String): Boolean {
        return selectedHorses.contains(horse)
    }

    internal fun getStable() {
        viewModelScope.launch {
            try {
                val result = StableApi.retrofitService.getStables()
                _stable.value = result
            } catch (e: Exception){

            }
        }
    }

    internal fun getAllLessons2() {
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.getLessons("",
                    mutableListOf(), selectedDate, null,false, null)
                _lessons2.value = listResult
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    internal fun createLessons(token: String?) {
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.postNewLessons("Bearer $token", PostingLessonEntity(selectedArena,selectedDate,selectedTimeFrom,selectedTimeTo))
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    fun setDate(date: String){
        selectedDate = date
    }

    fun setArena(arena: String){
        selectedArena = arena
    }

    fun setTimeFrom (time: Int) {
        selectedTimeFrom = time
    }

    fun setTimeTo (time: Int) {
        selectedTimeTo = time
    }
}