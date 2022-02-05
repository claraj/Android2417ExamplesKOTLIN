package com.example.maps_and_location

import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.maps_and_location.databinding.AttractionListItemBinding

interface AttractionListItemInteractionListener {
    fun onAttractionVisited(attractionId: Int, visited: Boolean)
    fun onAttractionSelected(attractionId: Int)
}

class RecyclerAdapter(private val attractions: List<Attraction>, private val listener: AttractionListItemInteractionListener): RecyclerView.Adapter<RecyclerAdapter.AttractionViewHolder>() {

    inner class AttractionViewHolder(private val binding: AttractionListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(attraction: Attraction) {
            val context = binding.thumbnail.context
            binding.thumbnail.setImageDrawable(getDrawable(context, attraction.imageResourceId))
            binding.attractionName.text = attraction.name
            binding.visitedCheckbox.isChecked = attraction.visited
            binding.visitedCheckbox.setOnCheckedChangeListener { checkbox, isChecked ->
                listener.onAttractionVisited(attraction.id, isChecked)
            }
            binding.root.setOnClickListener {
                listener.onAttractionSelected(attraction.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        return AttractionViewHolder(
            AttractionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
       val attraction = attractions[position]
        holder.bind(attraction)
    }

    override fun getItemCount(): Int {
        return attractions.size
    }
}