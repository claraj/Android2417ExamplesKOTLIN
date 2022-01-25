package com.example.redbluefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random


/**
 * A simple [Fragment] subclass.
 * Use the [RedFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

const val RANDOM_NUMBER_GENERATED = "com.example.redbluefragment.RANDOM_NUMBER_GENERATED"

class RedFragment : Fragment() {

    private lateinit var sendRandomNumberButton: Button

    private val randomNumberViewModel: RandomNumberViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RandomNumberViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_red, container, false)

        sendRandomNumberButton = view.findViewById(R.id.send_random_number_button)

        sendRandomNumberButton.setOnClickListener {
            sendRandomNumber()
        }

        return view
    }

    private fun sendRandomNumber() {
        val random = Random.nextInt(100)
        randomNumberViewModel.randomNumber = random
        // Could add data to the Bundle if needed. Here, only need to notify the Activity
        // that this Fragment is done so can send empty Bundle
        parentFragmentManager.setFragmentResult(RANDOM_NUMBER_GENERATED, Bundle.EMPTY)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() = RedFragment()
    }
}