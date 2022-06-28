package com.readytoride.ui.login

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.UserApi.LoginEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.network.UserApi.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginViewModel: ViewModel() {

    private val _token = MutableLiveData<TokenEntity>()
    val token: LiveData<TokenEntity> = _token

    internal fun loginUser(email: String, pwd: String) {
        viewModelScope.launch {
            try {
                val loginEntity = LoginEntity(email, pwd)
                var response = UserApi.retrofitService.login(loginEntity)
                _token.value = response
            } catch (e: Exception){
            }
        }
    }
}