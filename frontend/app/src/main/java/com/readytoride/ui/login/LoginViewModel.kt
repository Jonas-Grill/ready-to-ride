package com.readytoride.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.R
import com.readytoride.network.UserApi.LoginEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.network.UserApi.UserApi
import kotlinx.coroutines.launch
import java.lang.Exception


class LoginViewModel: ViewModel() {

    private val _token = MutableLiveData<TokenEntity>()
    private val _role = MutableLiveData<String>()

    val token: LiveData<TokenEntity> = _token
    val role : LiveData<String> = _role

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

    internal fun getUserRole(token: String) {
        viewModelScope.launch {
            try {
                var response = UserApi.retrofitService.getMyUser("Bearer $token")
                _role.value = response.role
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }
}