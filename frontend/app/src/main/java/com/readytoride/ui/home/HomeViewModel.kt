package com.readytoride.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi
import com.readytoride.ui.horse.Horse
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()
    val status: LiveData<String> = _status

    private fun getAllHorses() {
        viewModelScope.launch {
            val listResult = HorseApi.retrofitService.getHorses()
            _status.value = listResult
        }
    }
}