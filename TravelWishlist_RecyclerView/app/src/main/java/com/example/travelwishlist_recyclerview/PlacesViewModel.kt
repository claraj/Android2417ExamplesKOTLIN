package com.example.travelwishlist_recyclerview

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    // Example data added for testing. You may modify or remove.
    private val placeNames = mutableListOf<Place>(Place("Toronto"), Place("Patagonia, Chile"), Place("Auckland, NZ"))

    fun getPlaces(): List<Place> {
        return placeNames
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

/*
        position?.let {  // let lambda is called if position is not null. Within the lambda, position is Int not Int?
            placeNames.add(position, place)
            return position
        } ?: run {  // run lambda is called if position is null.
            placeNames.add(place)
            return placeNames.size - 1
        }
*/

        // Equivalent to,
        return if (position != null) {
            placeNames.add(position, place)
            position
        } else {
            placeNames.add(place)
            placeNames.size - 1
        }
    }

    fun deletePlace(position: Int) {
        placeNames.removeAt(position)
    }

    fun movePlace(from: Int, to: Int) {
        // Remove place and save value
        val place = placeNames.removeAt(from)
        // Insert into list at new position
        placeNames.add(to, place)
    }

    fun placeAt(position: Int): Place {
        return placeNames[position]
    }

    fun clear() {
        placeNames.clear()
    }
}