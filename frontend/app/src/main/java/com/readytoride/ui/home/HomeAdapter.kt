package com.readytoride.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.readytoride.R
import com.readytoride.ui.horse.Horse

class HomeAdapter(private val context: HomeFragment, private val dataset: List<NewsEntry>): BaseAdapter() {

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var image: ImageView

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): Any {
        return "Test String"
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val item = dataset[position]
        var convertView = convertView
        convertView = LayoutInflater.from(parent!!.context).inflate(R.layout.news_list_row, parent, false)
        title = convertView.findViewById(R.id.news_name)
        description = convertView.findViewById(R.id.desc)
        image = convertView.findViewById(R.id.image)
        title.text = context.resources.getString(item.titleStringResourceId)
        description.text = context.resources.getString(item.descriptionStringResourceId)
        image.setImageResource(item.imageResourceId)
        return convertView
    }
}