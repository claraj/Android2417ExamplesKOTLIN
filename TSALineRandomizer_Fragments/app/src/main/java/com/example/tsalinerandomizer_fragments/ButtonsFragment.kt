package com.example.tsalinerandomizer_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider


class ButtonsFragment : Fragment() {

    private lateinit var showRandomArrowButton: Button
    private lateinit var clearArrowButton: Button

    private val arrowViewModel: ArrowViewModel by lazy {
        ViewModelProvider(requireActivity()).get(ArrowViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_buttons, container, false)

        showRandomArrowButton = view.findViewById(R.id.chooseRandomArrow)
        clearArrowButton = view.findViewById(R.id.clearArrow)

        showRandomArrowButton.setOnClickListener {
            chooseRandomArrow()
        }

        clearArrowButton.setOnClickListener {
            arrowViewModel.clearArrow()
        }

        return view
    }

    private fun chooseRandomArrow() {
        // Choose a direction from the choices "LEFT" or "RIGHT",
        // which are stored in the ViewModel
        val directions = arrowViewModel.arrowDirections
        val selectedDirection = directions.random()
        // Update the ViewModel with the direction chosen.
        // ArrowFragment is observing this value so can update the display.
        arrowViewModel.currentArrowDirection.value = selectedDirection
    }

}