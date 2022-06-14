package com.readytoride.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _horses = MutableLiveData<List<HorseEntity>>()
    private val _horse = MutableLiveData<HorseEntity>()
    private val _races = MutableLiveData<List<String>>()
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    val horses: LiveData<List<HorseEntity>> = _horses
    val horse: LiveData<HorseEntity> = _horse
    val races: LiveData<List<String>> = _races

    init {
        getHorse()
    }

    internal fun getAllHorses() {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorses()
                _horses.value = listResult
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getHorse() {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorseById("62a8859472df15057eab7854")
                _horse.value = listResult
            } catch (e: Exception){
                _text.value = "Failure: ${e.message}"
            }
        }
    }

    private fun getAllRaces() {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorseColours()
                _races.value = listResult
            }catch (e: Exception) {
                _text.value = "Failure: ${e.message}"
            }
        }
    }
}