package com.example.aliens_game_firebase

data class Score(
    val playerName: String? = null,
    val playerId: String? = null,
    val score: Int = Int.MIN_VALUE)