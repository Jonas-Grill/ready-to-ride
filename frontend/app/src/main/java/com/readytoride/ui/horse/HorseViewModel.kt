package com.readytoride.ui.horse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class HorseViewModel : ViewModel() {
    private val _horses = MutableLiveData<MutableList<HorseEntity>>()
    val horses: LiveData<MutableList<HorseEntity>> = _horses

    internal fun getAllHorses() {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorses().toMutableList()
                _horses.value = listResult
            } catch (e: Exception){

            }
        }
    }
}