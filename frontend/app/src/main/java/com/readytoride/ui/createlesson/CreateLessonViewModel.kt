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
    private val _lessons2 = MutableLiveData<MutableList<LessonEntity>>()
    private val _text = MutableLiveData<String>()
    private val _stable = MutableLiveData<StableEntity>()
    val stable: LiveData<StableEntity> = _stable
    val lessons: LiveData<MutableList<LessonEntity>> = _lessons
    val lessons2: LiveData<MutableList<LessonEntity>> = _lessons2
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
                println("Success")
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
                println(e.message)
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