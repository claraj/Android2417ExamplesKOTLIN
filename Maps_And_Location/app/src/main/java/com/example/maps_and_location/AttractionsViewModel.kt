package com.example.maps_and_location

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class AttractionsViewModel : ViewModel() {

    private val attractions = listOf(
        Attraction(1, "Minnehaha Falls", 44.9153288,-93.2285102, R.drawable.minnehaha_falls),
        Attraction(2, "Minneapolis Institute of Art", 44.9585001,-93.2907275, R.drawable.minneapolis_institute_of_art),
        Attraction(3, "Stone Arch Bridge", 44.9807377,-93.2555955, R.drawable.stone_arch_bridge)
    )

    val attractionsList = MutableLiveData(attractions)

    fun setVisited(locationId: Int, visited: Boolean) {
        val attraction = attractions.find{ it.id == locationId }
        attraction?.let {
            it.visited = visited
        }
        attractionsList.value = attractions
    }

    fun getAttractions(): List<Attraction> {
        return attractionsList.value ?: listOf()
    }


//    private val _index = MutableLiveData<Int>()
//    val text: LiveData<String> = Transformations.map(_index) {
//        "Hello world from section: $it"
//    }
//
//    fun setIndex(index: Int) {
//        _index.value = index
//    }
}