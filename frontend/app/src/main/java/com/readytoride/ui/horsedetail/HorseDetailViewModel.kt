package com.readytoride.ui.horsedetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class HorseDetailViewModel : ViewModel() {
    private val _horse = MutableLiveData<HorseEntity>()
    val horse: LiveData<HorseEntity> = _horse

    fun getHorse(id: String) {
        viewModelScope.launch {
            try {
                val listResult = HorseApi.retrofitService.getHorseById(id)
                _horse.value = listResult
            } catch (e: Exception){

            }
        }
    }
}