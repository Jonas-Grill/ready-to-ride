package com.readytoride.ui.trainer

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class Trainer(
    @StringRes val trainerId: Int,
    @StringRes val trainerStringResourceId: Int,
    @StringRes val ageStringResourceId: Int,
    @StringRes val focusStringResourceId: Int,
    @StringRes val qualificationStringResourceId: Int,
    @StringRes val descriptionStringResourceId: Int,
    val achievementsStringResourceId: List<List<Int>>,
    @DrawableRes val trainerimageResourceId: List<Int>
) {

}