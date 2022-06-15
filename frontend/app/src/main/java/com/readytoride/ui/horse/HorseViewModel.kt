package com.readytoride.ui.horse

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import kotlinx.coroutines.launch

class HorseViewModel : ViewModel() {
    var horses: MutableList<HorseEntity> = mutableListOf()

    fun getHorseList() {
        viewModelScope.launch {
            try {
                horses = HorseApi.retrofitService.getHorses().toMutableList()
            } catch (e: Exception) {

            }
        }
    }
}