package com.readytoride.ui.horsedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class HorseDetailViewModel : ViewModel() {
    var horse: HorseEntity = HorseEntity("", "", 0, "", 0, "", "", "", "", listOf(""))

    fun getHorse(id: String) {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorseById(id)
                horse = listResult
            } catch (e: Exception){
            }
        }
    }
}