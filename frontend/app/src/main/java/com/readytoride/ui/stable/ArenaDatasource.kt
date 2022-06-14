package com.readytoride.ui.stable

import com.readytoride.R

class ArenaDatasource() {
    fun loadArenas (): List<Arena> {
        return listOf<Arena>(
            Arena(R.string.arenaname1, R.string.arenasize1),
            Arena(R.string.arenaname2, R.string.arenasize1),
            Arena(R.string.arenaname3, R.string.arenasize1),
            Arena(R.string.arenaname4, R.string.arenasize1),
            Arena(R.string.arenaname5, R.string.arenasize1)
        )
    }
}