package com.readytoride.ui.home

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.Layout
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.ListAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import com.readytoride.R
import com.readytoride.network.NewsApi.NewsEntity
import com.readytoride.network.NewsApi.PostNewsEntity
import kotlinx.android.synthetic.main.add_news_dialog.*
import kotlinx.android.synthetic.main.fragment_home.*

class AddNewsDialog(val fragment: HomeFragment): DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.add_news_dialog, container, false)

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        homeViewModel.newNews.observe(viewLifecycleOwner) {
            this.dismiss()
        }

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val token: String = sharedPref?.getString("token", "defaultToken").toString()

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
            val finalCaption: String = editTitle.text.toString()
            val finalText: String = editDescr.text.toString()
            val newNews: PostNewsEntity = PostNewsEntity(finalCaption, finalText, "all")
            if(token != "defaultToken") {
                homeViewModel.postNews(token, newNews)
            }
        }
    }

    override fun onDestroy() {
        /*context.let {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
            fragmentManager.let {
                val currentFragment = fragment
                currentFragment?.let {
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.detach(it)
                    fragmentTransaction.attach(it)
                    fragmentTransaction.commit()
                }
            }
        }*/
        val fragTransaction = parentFragmentManager.beginTransaction()
        fragTransaction.detach(fragment)
        fragTransaction.attach(fragment)
        fragTransaction.commit()
        super.onDestroy()
    }
}