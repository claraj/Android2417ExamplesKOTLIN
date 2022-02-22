package com.example.travelwishlist_recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel: ViewModel() {

    // Example data added for testing. You may modify or remove.
    // TODO will replace with data from API
    private val placeNames = mutableListOf<Place>(
        Place(0, "Toronto", starred = true),
        Place(1, "Patagonia, Chile"),
        Place(2, "Auckland, NZ"))


    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData(listOf<Place>())

    init {
        getPlaces()
    }

    fun getPlaces() {
        // return placeNames
        viewModelScope.launch(Dispatchers.IO) {
            val places = placeRepository.getAllPlaces()
            allPlaces.postValue(places)
        }
    }

    fun addNewPlace(place: Place, position: Int? = null): Int {

        /** If the place's name is not already in the list, adds it to the list.
         * If position is not specified, add at that position.
         * Otherwise, add place to the end.
         *
         * Returns the position that the new place was added
         *
         * If a place name is in the list, return -1.
         * */

        if (placeNames.any { it.name.uppercase() == place.name.uppercase() }) {
            // Already there, return -1 to indicate not added
            return -1
        }

        return if (position != null) {
            // position is converted to an Int and will not be null
            // Kotlin does a "smart cast" on the position value from
            // Int? to Int, due to the null check
            // An if-statement null check is appropriate for parameters
            // which are vals, but can be nullable types
            placeNames.add(position, place)
            position
        } else {
            // position remains an Int? here and will be null
            placeNames.add(place)
            placeNames.lastIndex
        }
    }

    fun deletePlace(position: Int): Place {
        return placeNames.removeAt(position)
    }

    fun movePlace(from: Int, to: Int) {
        // Remove place and save value
        val place = placeNames.removeAt(from)
        // Insert into list at new position
        placeNames.add(to, place)
    }

    fun clear() {
        placeNames.clear()
    }

    fun updatePlace(place: Place): Int {

        // Update place and return position in list.

        // Since Place is an object and they are passed by reference,
        // if something else changes place then the place in the list
        // held by ViewModel also updates so when the data is a list, we
        // don't actually need to do anything. If the data in in a database
        // or on an API server, we would need to make sure the stored data is also
        // updated.

        return placeNames.indexOf(place)

    }
}