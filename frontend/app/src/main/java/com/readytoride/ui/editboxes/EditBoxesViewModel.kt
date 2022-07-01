package com.readytoride.ui.editboxes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.readytoride.network.StableApi.StableApi
import com.readytoride.network.StableApi.StableEntity
import kotlinx.coroutines.launch
import java.lang.Exception

class EditBoxesViewModel : ViewModel() {
    private val _stable = MutableLiveData<StableEntity>()
    val stable: LiveData<StableEntity> = _stable

    internal fun getStable() {
        viewModelScope.launch {
            try {
                val listResult = StableApi.retrofitService.getStables()
                _stable.value = listResult
            } catch (e: Exception){

            }
        }
    }

    internal fun setStable(stableEdited: StableEntity){
        viewModelScope.launch {
            try {
                val stableEntity = stableEdited
                val token = " " //TODO: Richtigen Token laden
                var response = StableApi.retrofitService.updateStable(token, stableEntity)
            } catch (e: Exception){

            }
        }
    }
}