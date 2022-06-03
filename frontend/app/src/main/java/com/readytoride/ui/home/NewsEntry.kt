package com.readytoride.ui.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

class NewsEntry (
    @StringRes val newsId: Int,
    @StringRes val titleStringResourceId: Int,
    @StringRes val descriptionStringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)
{

}