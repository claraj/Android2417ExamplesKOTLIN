package com.example.aliens_game_firebase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MAIN_ACTIVITY"

const val WELCOME_REQUEST_KEY = "Welcome"
const val GAME_REQUEST_KEY = "Play Game"
const val HIGH_SCORE_REQUEST_KEY = "High scores"

const val STORAGE_PLAYER_NAME_KEY = "player name"


class MainActivity : AppCompatActivity() {

    private val gameSpeed = 500L // bigger numbers = slower = easier

    private val highScoresViewModel: HighScoresViewModel by lazy {
        ViewModelProvider(this).get(HighScoresViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val playerId = LocalStorage.fetchString("PLAYER_ID", this)
        val savedPlayerName = LocalStorage.fetchString("PLAYER_NAME", this)
        if (playerId != null) {
            highScoresViewModel.loadPlayer(playerId)
        }

        // show welcome fragment
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainerView, WelcomeFragment.newInstance(savedPlayerName), "WELCOME")
            .commit()

        supportFragmentManager.setFragmentResultListener(WELCOME_REQUEST_KEY, this) { requestKey, bundle ->

            Log.d(TAG, "welcome done, show game")
            val playerName = bundle.getString(PLAYER_NAME_RESULT_KEY)

            LocalStorage.writeString(STORAGE_PLAYER_NAME_KEY, playerName!!, this)
            val player = Player(playerName)
            if (playerName != savedPlayerName) {
                highScoresViewModel.saveNewPlayer(player)
            }

            // start game
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView, GameFragment.newInstance(gameSpeed), "GAME")
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(GAME_REQUEST_KEY, this) { requestKey, bundle ->
            val score = bundle.getInt(SCORE_RESULT_KEY)

            Log.d(TAG, "game done show scores")

            LocalStorage.writeInt(STORAGE_PLAYER_NAME_KEY, score, this)

            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.fragmentContainerView,
                    HighScoreFragment.newInstance(score, playerId),
                    "HIGH SCORES"
                )
                .commit()
        }

        supportFragmentManager.setFragmentResultListener(HIGH_SCORE_REQUEST_KEY, this) { requestKey, bundle ->

            Log.d(TAG, "play game again")
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainerView, GameFragment.newInstance(gameSpeed), "GAME")
                .commit()

        }

    }
}