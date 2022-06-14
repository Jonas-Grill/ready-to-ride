package com.readytoride.ui.stable

import com.readytoride.R

class BoxDatasource() {
    fun loadBoxes (): List<Box> {
        return listOf<Box>(
            Box(R.string.boxname1, R.string.boxsize1, R.string.boxprice1, R.string.boxnum1),
            Box(R.string.boxname2, R.string.boxsize2, R.string.boxprice2, R.string.boxnum2),
            Box(R.string.boxname3, R.string.boxsize3, R.string.boxprice3, R.string.boxnum3)
        )
    }
}