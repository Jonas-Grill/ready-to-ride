package com.readytoride.ui.horse

import com.readytoride.R

class HorseDatasource() {

    fun loadHorses(): List<Horse> {
        return listOf<Horse>(
            Horse(
                R.string.id1,
                R.string.horsename1,
                R.string.horseheight1,
                R.string.race1,
                R.string.age1,
                R.string.colour1,
                R.string.difficulty1,
                R.string.description1,
                arrayListOf(R.drawable.horse1, R.drawable.horse4, R.drawable.horse5)
            ),
            Horse(
                R.string.id2,
                R.string.horsename2,
                R.string.horseheight2,
                R.string.race2,
                R.string.age2,
                R.string.colour2,
                R.string.difficulty2,
                R.string.description2,
                arrayListOf(R.drawable.horse2, R.drawable.horse6)
            ),
            Horse(
                R.string.id3,
                R.string.horsename3,
                R.string.horseheight3,
                R.string.race3,
                R.string.age3,
                R.string.colour3,
                R.string.difficulty3,
                R.string.description3,
                arrayListOf(R.drawable.horse3, R.drawable.horse10, R.drawable.horse9)
            ),
            Horse(
                R.string.id4,
                R.string.horsename4,
                R.string.horseheight4,
                R.string.race4,
                R.string.age4,
                R.string.colour4,
                R.string.difficulty1,
                R.string.description4,
                arrayListOf(R.drawable.horse4, R.drawable.horse7, R.drawable.horse8)
            ),
            Horse(
                R.string.id5,
                R.string.horsename5,
                R.string.horseheight5,
                R.string.race5,
                R.string.age5,
                R.string.colour5,
                R.string.difficulty1,
                R.string.description5,
                arrayListOf(R.drawable.horse5, R.drawable.horse7, R.drawable.horse4)
            ),
            Horse(
                R.string.id6,
                R.string.horsename6,
                R.string.horseheight6,
                R.string.race6,
                R.string.age6,
                R.string.colour6,
                R.string.difficulty4,
                R.string.description1,
                arrayListOf(R.drawable.horse6, R.drawable.horse2)
            ),
            Horse(
                R.string.id7,
                R.string.horsename7,
                R.string.horseheight7,
                R.string.race7,
                R.string.age7,
                R.string.colour7,
                R.string.difficulty1,
                R.string.description2,
                arrayListOf(R.drawable.horse7, R.drawable.horse4, R.drawable.horse1)
            ),
            Horse(
                R.string.id8,
                R.string.horsename8,
                R.string.horseheight8,
                R.string.race8,
                R.string.age8,
                R.string.colour8,
                R.string.difficulty3,
                R.string.description3,
                arrayListOf(R.drawable.horse8, R.drawable.horse1)
            ),
            Horse(
                R.string.id9,
                R.string.horsename9,
                R.string.horseheight9,
                R.string.race9,
                R.string.age9,
                R.string.colour1,
                R.string.difficulty2,
                R.string.description4,
                arrayListOf(R.drawable.horse9, R.drawable.horse3)
            ),
            Horse(
                R.string.id10,
                R.string.horsename10,
                R.string.horseheight10,
                R.string.race1,
                R.string.age10,
                R.string.colour2,
                R.string.difficulty1,
                R.string.description5,
                arrayListOf(R.drawable.horse10, R.drawable.horse3)
            )
        )
    }
}