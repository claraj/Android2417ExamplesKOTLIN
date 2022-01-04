package com.example.auroraforecast

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "REPORT RECYCLER"

class ReportRecyclerAdapter(var dataSet: List<Report> ):
    RecyclerView.Adapter<ReportRecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val dateTV: TextView = view.findViewById(R.id.dateTextView)
        val kpTV: TextView = view.findViewById(R.id.kpTextView)
        val statusTV: TextView = view.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.report_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val record = dataSet[position]

        holder.dateTV.text = record.stringDate

        val kp = record.kp
        holder.kpTV.text = "$kp"

        // Change the color of the KP number's text based on the numeric value

        when (kp) {
            in 0..3 -> {
                holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.low))
            }
            in 4..6 -> {
                holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.medium))
            }
            else -> {
                holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.high))
            }
        }

        holder.statusTV.text = record.status
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

}