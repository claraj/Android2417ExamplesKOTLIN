package com.example.aliens_game_firebase

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.snackbar.Snackbar

private const val ARG_PLAYER_NAME = "player_name"

private const val TAG = "WELCOME_FRAGMENT"

const val PLAYER_NAME_RESULT_KEY = "Player name"

class WelcomeFragment : Fragment() {

    private var playerName: String? = null

    private lateinit var playerNameEditText: EditText
    private lateinit var startPlayButton: Button
    private lateinit var welcomeMessageTextView: TextView

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "frag started")

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            playerName = it.getString(ARG_PLAYER_NAME)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainView = inflater.inflate(R.layout.fragment_welcome, container, false)
        playerNameEditText = mainView.findViewById(R.id.player_name_edit_text)
        startPlayButton = mainView.findViewById(R.id.play_button)
        welcomeMessageTextView = mainView.findViewById(R.id.welcome_player_name_text_view)

        playerName?.let {
            // If a player's name has been set, display welcome message
            welcomeMessageTextView.text = getString(R.string.welcome, playerName)
            playerNameEditText.visibility = View.GONE
            welcomeMessageTextView.visibility = View.VISIBLE

        } ?: run {
            // otherwise, show edit text to enter name
            playerNameEditText.visibility = View.VISIBLE
            welcomeMessageTextView.visibility = View.GONE
        }

        startPlayButton.setOnClickListener {
            requireUsernameAndStartGame()
        }

        return mainView

    }

    private fun requireUsernameAndStartGame() {

        if (playerName != null) {
            val bundle = bundleOf(PLAYER_NAME_RESULT_KEY to playerName)
            setFragmentResult(WELCOME_REQUEST_KEY, bundle)
        }
        else {
            val newPlayerName = playerNameEditText.text.toString()
            if (newPlayerName.isBlank()) {
                Snackbar.make(requireView(), "Enter your name", Snackbar.LENGTH_SHORT).show()
            } else {
                val bundle = bundleOf(PLAYER_NAME_RESULT_KEY to playerName)
                setFragmentResult(WELCOME_REQUEST_KEY, bundle)
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param player_name Parameter 1.
         * @return A new instance of fragment WelcomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(player_name: String?) =
            WelcomeFragment().apply {
                arguments = bundleOf(ARG_PLAYER_NAME to player_name)
            }
    }
}