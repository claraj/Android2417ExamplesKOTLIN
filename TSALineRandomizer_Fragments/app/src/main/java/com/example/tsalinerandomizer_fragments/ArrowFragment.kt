package com.example.tsalinerandomizer_fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider

private const val ARG_ARROW_DIRECTION = "arrow_direction"

/**
 * A simple [Fragment] subclass.
 * Use the [ArrowFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ArrowFragment : Fragment() {

    private val TAG = "ARROW_FRAGMENT"

    private var initialArrowDirection: String? = null

    private lateinit var leftArrowImageView: ImageView
    private lateinit var rightArrowImageView: ImageView

    private val arrowViewModel: ArrowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ArrowViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            initialArrowDirection = it.getString(ARG_ARROW_DIRECTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_arrow, container, false)
        leftArrowImageView = view.findViewById(R.id.leftArrowImageView)
        rightArrowImageView = view.findViewById(R.id.rightArrowImageView)

        // If an initial arrow direction is set via the newInstance parameters,
        // show the appropriate arrow.
        setArrowDirection(initialArrowDirection)

        // Observe the arrowDirection in the ViewModel.
        arrowViewModel.currentArrowDirection.observe(requireActivity()) {
            newDirection -> setArrowDirection(newDirection)
        }

        return view
    }


    private fun setArrowDirection(direction: String?) {

        Log.d(TAG, "Direction is $direction")

        when (direction) {
            null -> {
                clearArrow()
            }
            "LEFT" -> {
                rightArrowImageView.visibility = View.INVISIBLE
                leftArrowImageView.visibility = View.VISIBLE
            }
            "RIGHT" -> {
                rightArrowImageView.visibility = View.VISIBLE
                leftArrowImageView.visibility = View.INVISIBLE
            }
            else -> {
                // TODO - what should we do if an invalid direction is specified?
            }
        }
    }


    private fun clearArrow() {
        rightArrowImageView.visibility = View.INVISIBLE
        leftArrowImageView.visibility = View.INVISIBLE
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ArrowFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            ArrowFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_ARROW_DIRECTION, param1)
                }
            }
    }
}