package com.readytoride.ui.trainer

import com.readytoride.R
import com.readytoride.ui.horse.Horse

class TrainerDatasource {
    fun loadTrainer(): List<Trainer> {
        return listOf<Trainer>(
            Trainer(R.string.trainername1, R.string.qualification1, R.drawable.trainer1),
            Trainer(R.string.trainername2, R.string.qualification2, R.drawable.trainer2),
            Trainer(R.string.trainername3, R.string.qualification3, R.drawable.trainer3),
            Trainer(R.string.trainername4, R.string.qualification4, R.drawable.trainer4),
            Trainer(R.string.trainername5, R.string.qualification5, R.drawable.trainer5),
        )
    }
}