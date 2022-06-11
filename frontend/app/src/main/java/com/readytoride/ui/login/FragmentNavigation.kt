package com.readytoride.ui.login

import androidx.fragment.app.Fragment
import java.util.*

interface FragmentNavigation {
    fun navigateFrag(fragment: Fragment, addtoStack: Boolean)
}