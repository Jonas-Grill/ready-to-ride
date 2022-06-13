package com.readytoride.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.readytoride.R
import kotlinx.android.synthetic.main.news_detail_dialog.*
import kotlinx.android.synthetic.main.news_detail_dialog.view.*

class NewsDetailDialog(private val title: CharSequence, private val description: CharSequence): DialogFragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.news_detail_dialog, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.news_title.text = title
        view.news_desc.text = description
    }
}