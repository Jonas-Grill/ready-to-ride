package com.readytoride.ui.trainerdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class TrainerDetailViewModel : ViewModel() {
    private val _trainer = MutableLiveData<UserEntity>()
    val trainer: LiveData<UserEntity> = _trainer

    fun getTrainer(id: String) {
        viewModelScope.launch {
            try {
                val token = ""
                val listResult = UserApi.retrofitService.getUserById(token, id)
                _trainer.value = listResult
            } catch (e: Exception){

            }
        }
    }
}