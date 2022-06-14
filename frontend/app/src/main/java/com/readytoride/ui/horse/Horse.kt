package com.readytoride.ui.horse

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class Horse(
    @StringRes val horseId: Int,
    @StringRes val nameStringResourceId: Int,
    @StringRes val heightStringResourceId: Int,
    @StringRes val raceStringResourceId: Int,
    @StringRes val ageStringResourceId: Int,
    @StringRes val colourStringResourceId: Int,
    @StringRes val difficultyStringResourceId: Int,
    @StringRes val descriptionStringResourceId: Int,
    @DrawableRes val imageResourceId: List<Int>) {}