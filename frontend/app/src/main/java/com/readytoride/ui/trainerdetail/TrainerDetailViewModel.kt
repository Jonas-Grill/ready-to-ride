package com.readytoride.ui.trainerdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.UserApi.Name
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class TrainerDetailViewModel : ViewModel() {
    var trainer: UserEntity = UserEntity("", "", "", Name("",""), 0, "", 0, 0, "")

    fun getTrainer(id: String, token: String) {
        viewModelScope.launch {
            try {
                val listResult = UserApi.retrofitService.getUserById(token, id)
                trainer = listResult
            } catch (e: Exception){
            }
        }
    }
}