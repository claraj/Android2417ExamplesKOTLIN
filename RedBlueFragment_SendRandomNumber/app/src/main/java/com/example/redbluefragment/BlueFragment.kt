package com.example.redbluefragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider


/**
 * A simple [Fragment] subclass.
 * Use the [BlueFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BlueFragment : Fragment() {

    private lateinit var displayRandomTextView: TextView

    private val randomNumberViewModel: RandomNumberViewModel by lazy {
        ViewModelProvider(requireActivity()).get(RandomNumberViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_blue, container, false)
        displayRandomTextView = view.findViewById(R.id.random_number_textview)
        showRandomNumber()
        return view
    }

    private fun showRandomNumber() {
        val randomNumber = randomNumberViewModel.randomNumber
        displayRandomTextView.text = randomNumber.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment BlueFragment.
         */

        @JvmStatic
        fun newInstance() =
            BlueFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}