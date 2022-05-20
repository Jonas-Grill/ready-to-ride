package com.readytoride.ui.horse

import com.readytoride.R

class HorseDatasource() {

    fun loadHorses(): List<Horse> {
        return listOf<Horse>(
            Horse(R.string.horsename1, R.string.horseheight1, R.drawable.horse1),
            Horse(R.string.horsename2, R.string.horseheight2, R.drawable.horse2),
            Horse(R.string.horsename3, R.string.horseheight3, R.drawable.horse3),
            Horse(R.string.horsename4, R.string.horseheight4, R.drawable.horse4),
            Horse(R.string.horsename5, R.string.horseheight5, R.drawable.horse5),
            Horse(R.string.horsename6, R.string.horseheight6, R.drawable.horse6),
            Horse(R.string.horsename7, R.string.horseheight7, R.drawable.horse7),
            Horse(R.string.horsename8, R.string.horseheight8, R.drawable.horse8),
            Horse(R.string.horsename9, R.string.horseheight9, R.drawable.horse9),
            Horse(R.string.horsename10, R.string.horseheight10, R.drawable.horse10)
        )
    }
}