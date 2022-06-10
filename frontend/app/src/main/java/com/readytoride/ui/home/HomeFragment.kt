package com.readytoride.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.readytoride.R
import com.readytoride.databinding.FragmentHomeBinding
import com.readytoride.ui.homedetail.HomeDetail

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val myDataset = NewsDataSource().loadNewsFeed()
        val listView = view.findViewById<ListView>(R.id.newsList)
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
        /*listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val element = listView.getItemAtPosition(position) // The item that was clicked
                val action =
                val intent = Intent(this, )
                    Intent(this, HomeDetail::class.java)
                startActivity(intent)
            }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}