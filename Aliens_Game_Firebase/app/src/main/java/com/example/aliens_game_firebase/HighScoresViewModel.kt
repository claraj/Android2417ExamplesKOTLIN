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
                playerId.postValue(playerDocumentReference.result?.id)
            } else {
                Log.e(TAG, "Error saving player to Firebase", playerDocumentReference.exception)
            }
        }
    }


    fun setPlayerScore(playerId: String, score: Score) {
        // is this player's score higher than their previous best? if so, overwrite
         highScoresReference.document(playerId).get().addOnSuccessListener { highScoreForPlayerDocument ->
             if (highScoreForPlayerDocument.exists()) {
                 val previousBestScore = highScoreForPlayerDocument.toObject(Score::class.java)
                 // if there is a previous best score, and the previous best is lower than the
                 // new score, then update the document with the new score
                 if (previousBestScore?.score != null && previousBestScore.score < score.score){
                     highScoreForPlayerDocument.reference.update("score", score)
                 } else {
                     highScoresReference.add(score)
                 }

             }

         }
    }

    override fun onCleared() {
        super.onCleared()
        highScoresListener.remove()
        Log.d(TAG, "Removing listener")
    }
}