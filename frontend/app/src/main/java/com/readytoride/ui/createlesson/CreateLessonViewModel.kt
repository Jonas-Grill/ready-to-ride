package com.readytoride.ui.createlesson

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.LessonApi.LessonApi
import com.readytoride.network.LessonApi.LessonEntity
import com.readytoride.network.LessonApi.PostingLessonEntity
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateLessonViewModel : ViewModel() {
    var selectedDate:String = ""
    var selectedTimeFrom: Int = 0
    var selectedTimeTo: Int = 0
    var selectedArena: String = ""

    private val _lessons = MutableLiveData<MutableList<LessonEntity>>()
    private val _text = MutableLiveData<String>()
    private val _stable = MutableLiveData<StableEntity>()
    val stable: LiveData<StableEntity> = _stable
    val lessons: LiveData<MutableList<LessonEntity>> = _lessons
    val text: LiveData<String> = _text

    internal fun getStable() {
        viewModelScope.launch {
            try {
                val result = StableApi.retrofitService.getStables()
                _stable.value = result
            } catch (e: Exception){

            }
        }
    }

    internal fun getAllLessons() {
        // selectedDate, selectedDate, false, true
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.getLessons("",
                    mutableListOf(), selectedDate, selectedDate,false,true)
                _lessons.value = listResult
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    internal fun createLessons() {
        val token = "" //TODO: Token auslesen
        viewModelScope.launch {
            try {
                val listResult = LessonApi.retrofitService.postNewLessons(token, PostingLessonEntity(selectedArena,selectedDate,selectedTimeFrom,selectedTimeTo))
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    fun setDate(date: String){
        selectedDate = date
        println(selectedDate)
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