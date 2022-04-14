package com.example.aliens_game_firebase

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

private const val TAG = "SCORE_VIEW_MODEL"

class HighScoresViewModel: ViewModel() {

    /*
    * Two collections
    *
    * players
    *   id1
    *     |- name -> "alice"
    *   id2
    *     |- name -> "bob"
    *
    * high_scores
    *   id1
    *     |- player_id -> id1
    *        name -> "alice"
    *        score -> 400
    *   id2
    *     |- player_id -> id2
    *        name -> "bob"
    *        score -> 250
    *
    * */

    private val db = Firebase.firestore
    private val highScoresReference = db.collection("high_scores")
    private val playersReference = db.collection("players")

    val highScores = MutableLiveData<List<Score>>()
    val playerId = MutableLiveData<String>()

    var player = MutableLiveData<Player?>()

    private val highScoresListener = highScoresReference
        .orderBy("score", Query.Direction.DESCENDING)
        .limit(10)
        .addSnapshotListener { snapshot, error ->
            if (error != null) {
                Log.e(TAG,"Error getting high scores trees", error)
            }
            if (snapshot != null) {
                val scores = snapshot.toObjects(Score::class.java)
                highScores.postValue(scores)
            }
        }


    fun loadPlayer(playerId: String) {
        playersReference.document(playerId).get().addOnSuccessListener { playerDocument ->
            if (playerDocument.exists()) {
                player.postValue(playerDocument.toObject(Player::class.java))
            } else {
                player.postValue(null)
            }
        }
    }


    fun saveNewPlayer(player: Player) {
        playersReference.add(player).addOnCompleteListener { playerDocumentReference ->
            if (playerDocumentReference.isSuccessful) {
                val id = playerDocumentReference.result?.id
                playerId.postValue(id!!)
                Log.d(TAG, "New player id is $id")
            } else {
                Log.e(TAG, "Error saving player to Firebase", playerDocumentReference.exception)
            }
        }
    }


    fun setPlayerScore(score: Score) {
        // is this player's score higher than their previous best? if so, overwrite
        score.playerId?.let {
            highScoresReference.document(it).get().addOnSuccessListener { highScoreForPlayerDocument ->
                if (highScoreForPlayerDocument.exists()) {
                    val previousBestScore = highScoreForPlayerDocument.toObject(Score::class.java)
                    // if there is a previous best score, and the previous best is lower than the
                    // new score, then update the document with the new score
                    if (previousBestScore?.score != null && previousBestScore.score < score.score){
                        highScoreForPlayerDocument.reference.update("score", score)
                        Log.d(TAG, "New high score for user, updating firebase document")
                    }
                    else {
                       // the user already has a score, but it's higher than this one
                        Log.d(TAG, "Not a new score for user")
                    }

                } else {
                    highScoresReference.add(score)
                    Log.d(TAG, "First score for user, adding firebase document")
                }

            }
        }  ?: run {
            Log.w(TAG, "No player ID provided")
        }
    }

    override fun onCleared() {
        super.onCleared()
        highScoresListener.remove()
        Log.d(TAG, "Removing listener")
    }
}