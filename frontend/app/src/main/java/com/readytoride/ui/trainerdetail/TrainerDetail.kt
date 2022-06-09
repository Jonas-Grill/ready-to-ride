package com.readytoride.ui.trainerdetail

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
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.models.SlideModel
import com.readytoride.R
import com.readytoride.ui.trainer.TrainerDatasource

class TrainerDetail : Fragment() {

    companion object {
        fun newInstance() = TrainerDetail()
    }

    private lateinit var viewModel: TrainerDetailViewModel
    private lateinit var trainerId: String
    val args: TrainerDetailArgs by navArgs<TrainerDetailArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        trainerId = args.trainerId.toString()
        return inflater.inflate(R.layout.fragment_trainer_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrainerDetailViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewTrainername: TextView = view.findViewById(R.id.item_trainername_detail)
        val textViewAge: TextView = view.findViewById(R.id.item_age_trainer_detail)
        val textViewFocus: TextView = view.findViewById(R.id.item_focus_trainer_detail)
        val textViewQualification: TextView =
            view.findViewById(R.id.item_qualification_trainer_detail)
        val textViewDescription: TextView = view.findViewById(R.id.item_description_trainer_detail)

        val index: Int = trainerId.toInt()

        val trainer = TrainerDatasource().loadTrainer()[index - 1]

        textViewTrainername.text = getString(trainer.trainerStringResourceId)
        textViewAge.text = getString(trainer.ageStringResourceId)
        textViewFocus.text = getString(trainer.focusStringResourceId)
        textViewQualification.text = getString(trainer.qualificationStringResourceId)
        textViewDescription.text = getString(trainer.descriptionStringResourceId)

        val imageList = ArrayList<SlideModel>()

        for (image in trainer.trainerimageResourceId) {
            imageList.add(SlideModel(image))
        }

        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSliderTrainer)
        imageSlider.setImageList(imageList)

        val myDataset = trainer.achievementsStringResourceId
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_trainer_achievements)
        recyclerView.adapter = AchievementsItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        val bookingButton: Button = view.findViewById(R.id.button_book_trainer)
        bookingButton.setOnClickListener {
            val action = TrainerDetailDirections.actionTrainerDetail2ToNavLessons(trainerId, null)
            view.findNavController().navigate(action)
        }
    }
}