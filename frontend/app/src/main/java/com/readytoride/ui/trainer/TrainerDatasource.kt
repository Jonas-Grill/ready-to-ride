package com.readytoride.ui.trainer

import com.readytoride.R
import com.readytoride.ui.horse.Horse

class TrainerDatasource {
    fun loadTrainer(): List<Trainer> {
        return listOf<Trainer>(
            Trainer(
                R.string.id1,
                R.string.trainername1,
                R.string.age9,
                R.string.focus1,
                R.string.qualification1,
                R.string.description6,
                arrayListOf(
                    arrayListOf(R.string.date1, R.string.achievement1),
                    arrayListOf(R.string.date2, R.string.achievement2),
                    arrayListOf(R.string.date3, R.string.achievement3),
                    arrayListOf(R.string.date4, R.string.achievement4),
                    arrayListOf(R.string.date4, R.string.achievement1)
                ),
                arrayListOf(R.drawable.trainer1, R.drawable.trainer2)
            ),
            Trainer(
                R.string.id2,
                R.string.trainername2,
                R.string.age10,
                R.string.focus2,
                R.string.qualification2,
                R.string.description7,
                arrayListOf(
                    arrayListOf(R.string.date1, R.string.achievement2),
                    arrayListOf(R.string.date2, R.string.achievement3),
                    arrayListOf(R.string.date2, R.string.achievement4),
                    arrayListOf(R.string.date3, R.string.achievement1),
                    arrayListOf(R.string.date4, R.string.achievement2)
                ),
                arrayListOf(R.drawable.trainer2, R.drawable.trainer4)
            ),
            Trainer(
                R.string.id3,
                R.string.trainername3,
                R.string.age11,
                R.string.focus3,
                R.string.qualification3,
                R.string.description8,
                arrayListOf(
                    arrayListOf(R.string.date1, R.string.achievement1),
                    arrayListOf(R.string.date1, R.string.achievement3),
                    arrayListOf(R.string.date2, R.string.achievement4)
                ),
                arrayListOf(R.drawable.trainer3)
            ),
            Trainer(
                R.string.id4,
                R.string.trainername4,
                R.string.age12,
                R.string.focus4,
                R.string.qualification4,
                R.string.description9,
                arrayListOf(
                    arrayListOf(R.string.date1, R.string.achievement1),
                    arrayListOf(R.string.date1, R.string.achievement4),
                    arrayListOf(R.string.date5, R.string.achievement3)
                ),
                arrayListOf(R.drawable.trainer4, R.drawable.trainer5)
            ),
            Trainer(
                R.string.id5,
                R.string.trainername5,
                R.string.age13,
                R.string.focus5,
                R.string.qualification5,
                R.string.description10,
                arrayListOf(arrayListOf(R.string.date5, R.string.achievement2)),
                arrayListOf(R.drawable.trainer5, R.drawable.trainer1)
            )
        )
    }
}