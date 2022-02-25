package com.example.hydration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RatingBar
import java.time.DayOfWeek

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_DAY_OF_WEEK = "day_of_week"

/**
 * A simple [Fragment] subclass.
 * Use the [HydrationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HydrationFragment : Fragment() {

    private lateinit var dayOfWeek: String

    private lateinit var waterRatingBar: RatingBar
    private lateinit var addGlassButton: ImageButton
    private lateinit var resetGlassesButton: ImageButton

    private lateinit var waterRecord: WaterRecord

    private val waterViewModel by lazy {
        WaterViewModelFactory((requireActivity().application as HydrationApplication).repository)
            .create(WaterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            dayOfWeek = it.getString(ARG_DAY_OF_WEEK)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_hydration, container, false)

        waterRatingBar = view.findViewById(R.id.water_rating_bar)
        addGlassButton = view.findViewById(R.id.add_glass_button)
        resetGlassesButton = view.findViewById(R.id.reset_count_button)

        // TODO add event listeners to buttons

        waterViewModel.getRecordForDay(dayOfWeek).observe(requireActivity()) { wr ->
            if (wr == null) {
                waterViewModel.insertNewRecord(WaterRecord(dayOfWeek, 0))
            } else {
                waterRecord = wr
                waterRatingBar.progress = waterRecord.glasses

                addGlassButton.setOnClickListener {
                    addGlass()
                }

                resetGlassesButton.setOnClickListener {
                    resetGlasses()
                }

            }
        }

        return view
    }


    private fun addGlass() {
        // add 1 to total glasses - up a max of 5
        if (waterRecord.glasses < resources.getInteger(R.integer.max_glasses)) {
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
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HydrationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(dayOfWeek: String) =
            HydrationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DAY_OF_WEEK, dayOfWeek)
                }
            }
    }
}