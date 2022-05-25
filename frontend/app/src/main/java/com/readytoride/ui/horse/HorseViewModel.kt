package com.readytoride.ui.horse

import androidx.lifecycle.ViewModel

class HorseViewModel : ViewModel() {
    private var horseid: String = ""

    fun setHorseId (id: String) {
        horseid = id
    }

    fun getHorseId (): String {
        return horseid
    }
}