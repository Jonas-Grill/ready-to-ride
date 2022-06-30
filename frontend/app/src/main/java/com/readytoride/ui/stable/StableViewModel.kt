package com.readytoride.ui.stable

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class StableViewModel : ViewModel() {
    private val _stable = MutableLiveData<StableEntity>()
    val stable: LiveData<StableEntity> = _stable

    internal fun getStable() {
        viewModelScope.launch {
            try {
                val listResult = StableApi.retrofitService.getStable()
                _stable.value = listResult
            } catch (e: Exception){

            }
        }
    }
}