package com.example.travelwishlist_recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView

import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

interface OnListItemClickedListener {
    fun onMapRequestButtonClicked(place: Place)
    fun onStarredStatusChanged(place: Place, isStarred: Boolean)
}

class PlaceRecyclerAdapter(var places: List<Place>, private val onListItemClickedListener: OnListItemClickedListener):
    RecyclerView.Adapter<PlaceRecyclerAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(place: Place){

            Log.d("ADAPTER", "place $place")
            val placeNameText: TextView = view.findViewById(R.id.place_name)
            placeNameText.text = place.name

            val dateCreatedOnText = view.findViewById<TextView>(R.id.reason_to_visit)
            dateCreatedOnText.text = place.reason  // view.context.getString(R.string.created_on, place.formattedDate())
            val mapIcon = view.findViewById<ImageView>(R.id.map_icon)
            mapIcon.setOnClickListener {
                onListItemClickedListener.onMapRequestButtonClicked(place)
            }

            view.findViewById<CheckBox>(R.id.star_check).apply {
                // remove listener, if set
                setOnCheckedChangeListener(null)
                // set the state
                isChecked = place.starred
                // then re-add listener - otherwise setting the isChecked state calls the listener which causes
                // the recycler view to update ths item which sets the isStarred value which calls the listener
                // which updates the item...
                setOnCheckedChangeListener { checkbox, isChecked ->
                    onListItemClickedListener.onStarredStatusChanged(place, isChecked)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place)
    }

    override fun getItemCount(): Int {
        return places.size
    }
}