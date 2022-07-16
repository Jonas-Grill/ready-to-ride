package com.readytoride.ui.home

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.readytoride.R
import com.readytoride.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.news_detail_dialog.*
import com.readytoride.network.HorseApi.HorseApi
import com.readytoride.network.HorseApi.HorseEntity
import com.readytoride.network.LessonApi.PostingLessonEntity
import com.readytoride.network.NewsApi.NewsEntity
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var newsFeed: MutableList<NewsEntity> = mutableListOf()
    //val listLayout: ConstraintLayout = fragmentHome
    //val vto: Unit = listLayout.viewTreeObserver.addOnGlobalLayoutListener {

    //}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
    //This code section is used to process Changes of Objects mainly due to backend calls
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        context.let {
            val fragmentManager = (context as AppCompatActivity).supportFragmentManager
        }

        homeViewModel.getAllNews()
        homeViewModel.news.observe(viewLifecycleOwner) {
            newsFeed = it
            setNewsList()
        }

        //Beispiel wie Rolle des Users geholt / verwendet werden kann
        val sharedPref = activity?.getSharedPreferences(R.string.user_token.toString(), Context.MODE_PRIVATE)
        val role: String? = sharedPref?.getString("role", "DefaultRole")

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val newsButton: View = addNewsButton
        newsButton.setOnClickListener { view ->
            val layout = view.parent
            var addNewsDialog = AddNewsDialog(this)
            addNewsDialog.show(parentFragmentManager, "AddNewsDialog")
        }
    }
    fun getAllNews() {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        homeViewModel.getAllNews()
    }

    fun setNewsList() {
        val myDataset: MutableList<NewsEntity> = newsFeed
        val listView = binding.newsList
        listView.adapter = HomeAdapter(this, myDataset)
        if (listView.adapter != null) {
            var totalHeight: Int = 0;
            for (i in 0..(listView.adapter.count - 1)) {
                val listItem = listView.adapter.getView(i, null, listView);
                listItem.measure(0, 0);
                totalHeight += listItem.measuredHeight;
            }
            val params: ViewGroup.LayoutParams = listView.layoutParams
            params.height = totalHeight + (listView.dividerHeight * (listView.adapter.count - 1));
            listView.layoutParams = params
            listView.requestLayout();

        }
        listView.isClickable = true
        listView.setOnItemClickListener() { parent, view, position, id ->
            val context: HomeFragment
            var title = (listView.adapter as HomeAdapter).getTitle(position, null, listView)
            var desc = (listView.adapter as HomeAdapter).getDesc(position, null, listView)
            var dialog = NewsDetailDialog(title, desc)
            dialog.show(parentFragmentManager, "NewsDetailDialog")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
