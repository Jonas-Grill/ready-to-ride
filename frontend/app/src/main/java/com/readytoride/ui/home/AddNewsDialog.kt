package com.readytoride.ui.home

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.readytoride.R
import kotlinx.android.synthetic.main.add_news_dialog.*

class AddNewsDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.add_news_dialog, container, false)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cancelButton = cancel_button
        cancelButton.setOnClickListener {
            this.dismiss()
        }

        val editTitle = editTextTitle
        val editDescr = editTextDescription
        val addButton = add_button
        val textWatcher = object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val text = editTitle.text
                if(editTitle.text.toString() != "" && editDescr.text.toString() != "") {
                    add_button.isEnabled = true
                }
                else {
                    add_button.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        }
        editTitle.addTextChangedListener(textWatcher)
        editDescr.addTextChangedListener(textWatcher)

        addButton.setOnClickListener {
            TODO()//Nach Backend Anbindung News hinzufügen!!
            this.dismiss()
        }
    }
}