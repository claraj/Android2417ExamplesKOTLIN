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
        val dateTV: TextView
        val kpTV: TextView
        val statusTV: TextView

        init {
            dateTV = view.findViewById(R.id.dateTextView)
            kpTV = view.findViewById(R.id.kpTextView)
            statusTV = view.findViewById(R.id.statusTextView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.report_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.dateTV.text = dataSet[position].stringDate

        val kp = dataSet[position].kp
        holder.kpTV.text = "$kp"

        if (kp < 3) {
            holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.low))
        } else if (kp < 5) {
            holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.medium))
        }
        else {
            holder.kpTV.setTextColor(ContextCompat.getColor(holder.kpTV.context, R.color.high))
        }
        holder.statusTV.text = dataSet[position].status
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "Get item count ${dataSet.size}")
       return dataSet.size
    }

}