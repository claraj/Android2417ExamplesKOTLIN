package com.example.hydration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar

private const val ARG_DAY_OF_WEEK = "day_of_week"

class HydrationFragment : Fragment() {

    private val waterViewModel: WaterViewModel by lazy {
        val app = requireActivity().application as HydrationApplication
        WaterViewModelFactory(app.waterRepository, app.daysRepository).create(
            WaterViewModel::class.java
        )
    }

    private lateinit var waterRatingBar: RatingBar
    private lateinit var addGlassButton: ImageButton
    private lateinit var resetGlassesButton: ImageButton

    private lateinit var dayOfWeek: String

    private lateinit var waterRecord: WaterRecord

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayOfWeek = it.getString(ARG_DAY_OF_WEEK) ?:
                throw IllegalArgumentException("No day of week provided to HydrationFragment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hydration, container, false)
        waterRatingBar = view.findViewById(R.id.water_rating_bar)
        addGlassButton = view.findViewById(R.id.add_glass_button)
        resetGlassesButton = view.findViewById(R.id.reset_count_button)

        waterRatingBar.numStars = WaterRecord.MAX_GLASSES
        waterRatingBar.max = WaterRecord.MAX_GLASSES

        waterViewModel.getRecordForDay(dayOfWeek).observe(requireActivity()) { waterRecord ->
            if (waterRecord == null) {
                waterViewModel.insertNewRecord(WaterRecord(dayOfWeek, 0))
            } else {
                this.waterRecord = waterRecord
                waterRatingBar.progress = waterRecord.glasses

                // Add listeners only when data is available
                addGlassButton.setOnClickListener { addGlass() }
                resetGlassesButton.setOnClickListener { resetGlasses() }
            }
        }

        return view
    }

    private fun addGlass() {
       if (waterRecord.glasses < WaterRecord.MAX_GLASSES) {
            waterRecord.glasses++
            waterViewModel.updateRecord(waterRecord)
       }
    }

    private fun resetGlasses() {
        waterRecord.glasses = 0
        waterViewModel.updateRecord(waterRecord)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param dayOfWeek the day of the week this fragment will show a record for
         * @return A new instance of fragment HydrationFragment.
         */
        @JvmStatic
        fun newInstance(dayOfWeek: String): HydrationFragment {
            return HydrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DAY_OF_WEEK, dayOfWeek)
                }
            }
        }
    }
}

