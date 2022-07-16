package com.readytoride.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.UserApi.*
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.RequestBody
import java.lang.Exception

class RegisterViewModel: ViewModel() {

    private val _newUser = MutableLiveData<UserEntity>()
    private val _role = MutableLiveData<String>()

    val newUser: LiveData<UserEntity> = _newUser
    val role : LiveData<String> = _role

    internal fun registerUser(user: RequestBody) {
        viewModelScope.launch {
            try {
                var response = UserApi.retrofitService.postNewUser(user)
                _newUser.value = response
            } catch (e: Exception){
                println(e.message)
            }
        }
    }

}