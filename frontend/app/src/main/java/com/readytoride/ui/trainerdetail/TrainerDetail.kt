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
import com.readytoride.network.UserApi.Accomplishment
import com.readytoride.network.UserApi.Name
import com.readytoride.network.UserApi.UserEntity

class TrainerDetail : Fragment() {

    companion object {
        fun newInstance() = TrainerDetail()
    }

    private lateinit var viewModel: TrainerDetailViewModel
    private lateinit var trainerId: String
    private lateinit var trainer: UserEntity
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
        //viewModel.getTrainer(token, this.trainerId)
        this.trainer = viewModel.trainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewTrainername: TextView = view.findViewById(R.id.item_trainername_detail)
        val textViewAge: TextView = view.findViewById(R.id.item_age_trainer_detail)
        val textViewFocus: TextView = view.findViewById(R.id.item_focus_trainer_detail)
        val textViewQualification: TextView = view.findViewById(R.id.item_qualification_trainer_detail)
        val textViewDescription: TextView = view.findViewById(R.id.item_description_trainer_detail)

        //val index: Int = trainerId.toInt()

        //val trainer = TrainerDatasource().loadTrainer()[index - 1]

        val name: Name = this.trainer.name
        val nameString: String = name.firstName + name.lastName
        textViewTrainername.text = nameString

        textViewAge.text = getString(this.trainer.age)
        textViewFocus.text = this.trainer.focus

        val certificates: List<Accomplishment> = this.trainer.certificates
        var year: Int = 0
        var qual: String = ""
        for (certificate in certificates) {
            if (certificate.year > year){
                year = certificate.year
                qual = certificate.name
            }
        }
        textViewQualification.text = qual
        textViewDescription.text = this.trainer.description

        val imageList = ArrayList<SlideModel>()

        for (image in this.trainer.pictures) {
            imageList.add(SlideModel(image))
        }

        val imageSlider = view.findViewById<ImageSlider>(R.id.imageSliderTrainer)
        imageSlider.setImageList(imageList)

        val myDataset = trainer.achievements
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_trainer_achievements)
        recyclerView.adapter = AchievementsItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)

        val bookingButton: Button = view.findViewById(R.id.button_book_trainer)
        bookingButton.setOnClickListener {
            val action = TrainerDetailDirections.actionTrainerDetail2ToNavLessons(trainerId, "")
            view.findNavController().navigate(action)
        }
    }
}