package com.readytoride.ui.trainer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class TrainerViewModel : ViewModel() {
    private val _trainer = MutableLiveData<MutableList<UserEntity>>()
    val trainer: LiveData<MutableList<UserEntity>> = _trainer

    internal fun getAllTrainer() {
        viewModelScope.launch {
            try {
                val listResult = UserApi.retrofitService.getUsers().toMutableList()
                _trainer.value = listResult
            } catch (e: Exception){

            }
        }
    }
}