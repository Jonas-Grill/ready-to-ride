package com.readytoride.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel : ViewModel() {

    private val _horses = MutableLiveData<List<HorseEntity>>()
    private val _stables = MutableLiveData<StableEntity>()
    private val _races = MutableLiveData<List<String>>()
    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text
    val horses: LiveData<List<HorseEntity>> = _horses
    val stable: LiveData<StableEntity> = _stables
    val races: LiveData<List<String>> = _races

    init {
        getAllStables()
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

    private fun getAllStables() {
        viewModelScope.launch {
            try {
                val listResult = StableApi.retrofitService.getStables()
                _stables.value = listResult
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