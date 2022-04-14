package com.example.aliens_game_firebase

import com.google.firebase.firestore.DocumentReference


data class Score(
    val playerName: String? = null,
//    val playerId: String? = null,
    val player: DocumentReference? = null,
    val score: Int = Int.MIN_VALUE)