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
        return inflater.inflate(R.layout.fragment_blue, container, false)

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