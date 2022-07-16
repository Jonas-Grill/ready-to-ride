package com.readytoride.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.JsonObject
import com.readytoride.network.LessonApi.LessonApi
import com.readytoride.network.LessonApi.LessonEntity
import com.readytoride.network.UserApi.LoginEntity
import com.readytoride.network.UserApi.TokenEntity
import com.readytoride.network.UserApi.UserApi
import com.readytoride.network.UserApi.UserEntity
import com.squareup.moshi.Json
import kotlinx.coroutines.launch
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.Exception

class ProfileViewModel : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()
    private val _lessons = MutableLiveData<MutableList<LessonEntity>>()
    private val _cancelMsg = MutableLiveData<String>()
    val user: LiveData<UserEntity> = _user
    val lessons: LiveData<MutableList<LessonEntity>> = _lessons
    val cancelMsg: LiveData<String> = _cancelMsg

    internal fun getUserData(token: String) {
        viewModelScope.launch {
            try {
                var response = UserApi.retrofitService.getMyUser("Bearer $token")
                _user.postValue(response)
                //_user.value = response
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }
    internal fun getUserBookings(token: String) {
        viewModelScope.launch {
            try {
                var response = UserApi.retrofitService.getMyUserCalendar("Bearer $token")
                _lessons.postValue(response)
                //_lessons.value = response
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }
    internal fun updateUser(token: String, user: RequestBody) {
        viewModelScope.launch {
            try {
                val response = UserApi.retrofitService.updateUser("Bearer $token", user)
                _user.value = response
            }catch (e: Exception) {
                println("Failure: ${e.message}")
            }
        }
    }
    internal fun cancelUserBooking(token: String, lessonId: String) {
        viewModelScope.launch {
            try {
                val response = LessonApi.retrofitService.cancelLesson("Bearer $token", lessonId)
                _cancelMsg.value = "Erfolgreich storniert!"
            } catch (e: Exception){
                println("Failure: ${e.message}")
            }
        }
    }
}