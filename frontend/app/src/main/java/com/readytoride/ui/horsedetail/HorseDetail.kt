package com.readytoride.ui.horsedetail

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.readytoride.R
import com.readytoride.network.HorseApi.HorseEntity

class HorseDetail : Fragment() {

    companion object {
        fun newInstance() = HorseDetail()
    }

    private lateinit var viewModel: HorseDetailViewModel
    private lateinit var horseId: String
    private lateinit var horse: HorseEntity
    val args: HorseDetailArgs by navArgs<HorseDetailArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.horseId = args.horseId.toString()
        return inflater.inflate(R.layout.fragment_horse_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HorseDetailViewModel::class.java)
        viewModel.getHorse(this.horseId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewHorsename: TextView = view.findViewById(R.id.horsename_detail)
        val textViewHeight: TextView = view.findViewById(R.id.height_detail)
        val textViewRace: TextView = view.findViewById(R.id.race_detail)
        val textViewAge: TextView = view.findViewById(R.id.age_detail)
        val textViewColour: TextView = view.findViewById(R.id.colour_detail)
        val textViewDifficulty: TextView = view.findViewById(R.id.difficulty_detail)
        val textViewDescription: TextView = view.findViewById(R.id.horsedescription_detail)

        //val index: Int = horseId.toInt()
        //val horse = HorseDatasource().loadHorses()[index - 1]

        textViewHorsename.text = this.horse.name
        textViewHeight.text = getString(this.horse.height)
        textViewRace.text = this.horse.race
        textViewAge.text = getString(this.horse.age)
        textViewColour.text = this.horse.colour
        textViewDifficulty.text = this.horse.difficultyLevel
        textViewDescription.text = this.horse.description

        val imageList = ArrayList<SlideModel>()

        for (image in this.horse.pictures) {
            imageList.add(SlideModel(image))
        }

        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSliderTrainer)
        imageSlider.setImageList(imageList)

        val bookingButton: Button = view.findViewById(R.id.button)
        bookingButton.setOnClickListener {
            val action = HorseDetailDirections.actionNavHorseDetailToNavLessons("", this.horseId)
            view.findNavController().navigate(action)
        }
    }
}