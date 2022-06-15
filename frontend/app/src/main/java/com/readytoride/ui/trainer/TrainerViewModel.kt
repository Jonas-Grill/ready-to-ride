package com.readytoride.ui.trainer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch

class TrainerViewModel : ViewModel() {
    var trainers: MutableList<UserEntity> = mutableListOf()

    fun getTrainerList() {
        viewModelScope.launch {
            try {
                trainers = UserApi.retrofitService.getUsers().toMutableList()
            } catch (e: Exception) {

            }
        }
    }
}