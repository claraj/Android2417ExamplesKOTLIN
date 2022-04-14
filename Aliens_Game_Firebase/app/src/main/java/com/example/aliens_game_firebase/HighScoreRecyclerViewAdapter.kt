package com.example.aliens_game_firebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HighScoreRecyclerViewAdapter(var highScores: List<Score>, private val playerId: String?):
    RecyclerView.Adapter<HighScoreRecyclerViewAdapter.HighScoreViewHolder>() {

    inner class HighScoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val playerNameTextView: TextView = itemView.findViewById(R.id.player_name_text_view)
        val scoreTextView: TextView = itemView.findViewById(R.id.score_text_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.high_score_list_item, parent, false)
        return HighScoreViewHolder(view)
    }

    override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
        val score = highScores[position]
        holder.playerNameTextView.text = "name " + score.playerName
        holder.scoreTextView.text = "${score.score}"
        if (score.playerId == playerId) {
            val highlightColor = holder.playerNameTextView.context.getColor(R.color.purple_500)
            holder.playerNameTextView.setTextColor(highlightColor)
            // this player's score - highlight
        }
    }

    override fun getItemCount(): Int {
        return highScores.size
    }
}