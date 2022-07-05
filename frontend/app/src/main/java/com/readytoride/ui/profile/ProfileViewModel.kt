package com.readytoride.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.UserApi.LoginEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> = _user

    internal fun getUserData(token: String) {
        viewModelScope.launch {
            try {
                var response = UserApi.retrofitService.getMyUser("Bearer $token")
                _user.value = response
            } catch (e: Exception){
            }
        }
    }
}