package com.readytoride.ui.contact

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.readytoride.R

class ContactFragment : Fragment() {

    companion object {
        fun newInstance() = ContactFragment()
    }

    private lateinit var viewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_contact, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        // TODO: Use the ViewModel

        //Logik für Auslesen von Contact Formular

        val btn_send = findViewById(R.id.sentButton) as Button
        btn_send.setOnClickListener {
            EditText textName = (EditText)findViewById(R.id.inputName);
            textName.getText();
            EditText textMail = (EditText)findViewById(R.id.inputMail);
            textMail.getText();
            EditText textMail = (EditText)findViewById(R.id.inputMessage);
            textMessage.getText();
        }

    }



    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById(R.id.sentButton).setOnClickListener(this)
    }

    fun onClick(view: View?) {
        when (view?.id) {
            R.id.sentButton -> {
                // your code logic here
            }
        }
    }*/
}