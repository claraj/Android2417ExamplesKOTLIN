package com.example.aliens_game_firebase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


private const val ARG_SCORE = "score"
private const val ARG_PLAYER_ID = "player id"

/**
 * A simple [Fragment] subclass.
 * Use the [HighScoreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HighScoreFragment : Fragment() {

    private var latestScore: Int? = null
    private var playerId: String? = null

    private lateinit var highScoreRecyclerView: RecyclerView
    private lateinit var latestScoreTextView: TextView
    private lateinit var playAgainButton: Button

    private val highScoresViewModel: HighScoresViewModel by lazy {
        ViewModelProvider(requireActivity()).get(HighScoresViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            latestScore = it.getInt(ARG_SCORE)
            playerId = it.getString(ARG_PLAYER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainView = inflater.inflate(R.layout.fragment_high_score, container, false)

        highScoreRecyclerView = mainView.findViewById(R.id.high_score_list)
        latestScoreTextView = mainView.findViewById(R.id.latest_score_label)
        playAgainButton = mainView.findViewById(R.id.play_again_button)

        latestScoreTextView.text = getString(R.string.score, latestScore)

        playAgainButton.setOnClickListener {
            setFragmentResult(HIGH_SCORE_REQUEST_KEY, bundleOf())  // empty bundle
        }

        highScoreRecyclerView.layoutManager = LinearLayoutManager(requireActivity())

        val adapter = HighScoreRecyclerViewAdapter(listOf(), playerId)
        highScoreRecyclerView.adapter = adapter

        highScoresViewModel.highScores.observe(requireActivity()) { scores ->
            adapter.highScores = scores
            adapter.notifyDataSetChanged()
        }

        return mainView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param score user's most recent score.
         * @return A new instance of fragment HighScoreFragment.
         */
        @JvmStatic
        fun newInstance(score: Int, playerId: String?) =
            HighScoreFragment().apply {
                arguments = bundleOf(
                    ARG_SCORE to score,
                    ARG_PLAYER_ID to playerId)
            }
    }
}