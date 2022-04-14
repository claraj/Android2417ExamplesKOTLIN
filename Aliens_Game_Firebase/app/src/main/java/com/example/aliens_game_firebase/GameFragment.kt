package com.example.aliens_game_firebase

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult

private const val ARG_SPEED = "game speed"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val TAG = "GAME_FRAGMENT"

const val SCORE_RESULT_KEY = "Score"

class GameFragment : Fragment() {

    private var speed: Long = 500
    private val lives = 3
    private val pointsPerAlien = 100

    private val alienViewIds = listOf(
        R.id.alien1,
        R.id.alien2,
        R.id.alien3,
        R.id.alien4,
        R.id.alien5,
    )

    private lateinit var alienViews: List<View>

    private var score = 0
    private var aliensShown = 0
    private var aliensHit = 0
    var gameRunnable: Runnable?  = null
    private var vibrator: Vibrator? = null
    lateinit var gameHandler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            speed = it.getLong(ARG_SPEED, speed)
        }
    }

    var show = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val mainView = inflater.inflate(R.layout.fragment_game, container, false)

        alienViews = alienViewIds.map { mainView.findViewById<View>(it) }
        alienViews.forEach { alien -> alien.setOnClickListener { alienTapped(it) } }

        vibrator = requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        //https://stackoverflow.com/questions/18459122/play-sound-on-button-click-android
        val mediaPlayer = MediaPlayer.create(requireActivity(), R.raw.ping)

        startGame()
        return mainView
    }

//     or https://hiteshkrsahu.medium.com/create-a-repeating-task-using-coroutine-e494ebae65c2
    private fun startGame() {
         gameHandler = Handler(Looper.getMainLooper())
         gameRunnable = Runnable {
             if (isGameOver()){
                 gameHandler.removeCallbacks(gameRunnable!!)
                 setFragmentResult(GAME_REQUEST_KEY, bundleOf(SCORE_RESULT_KEY to score))

             } else {
                 showHideAlien()
                 gameHandler.postDelayed(gameRunnable!!, speed)
             }
        }
        gameRunnable?.run()
    }


    private fun showHideAlien() {

//        ForGameOver()

        Log.d(TAG, "show hide $show")

        // hide all the aliens
        alienViews.forEach { it.visibility = View.INVISIBLE }

        if (show) {
            aliensShown++
            val alien = alienViews.random()
            alien.visibility = View.VISIBLE
        }

        show = !show

    }

    private fun isGameOver() : Boolean {

        // If 10 aliens have been shown, and user has hit 7, they are out of lives

        Log.d(TAG, "shown $aliensShown hit $aliensHit score $score")

        if (aliensHit + lives <= aliensShown) {
            Log.d(TAG, "Game over, score $score")
            return true
        }

        return false
    }


    private fun alienTapped(alien: View) {
        if (alien.visibility == View.VISIBLE) {
            score += pointsPerAlien
            alien.visibility = View.INVISIBLE
            vibrator?.vibrate(100)
            aliensHit++
        }
    }

    override fun onStop() {
        super.onStop()
        gameHandler.removeCallbacks(gameRunnable!!)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param speed Parameter 1.
         * @return A new instance of fragment GameFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(speed: Long) =
            GameFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_SPEED, speed)
                }
            }
    }
}