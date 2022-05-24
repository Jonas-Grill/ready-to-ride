package com.readytoride.ui.horsedetail

import android.os.Build.ID
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.readytoride.R

class HorseDetail : Fragment() {

    companion object {
        fun newInstance() = HorseDetail()
    }

    private lateinit var viewModel: HorseDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_horse_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HorseDetailViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val imageList = ArrayList<SlideModel>()

        imageList.add(SlideModel(R.drawable.horse1))
        imageList.add(SlideModel(R.drawable.horse4))
        imageList.add(SlideModel(R.drawable.horse5))

        val imageSlider = view.findViewById<ImageSlider>(R.id.imageView3)
        imageSlider.setImageList(imageList)
    }

}