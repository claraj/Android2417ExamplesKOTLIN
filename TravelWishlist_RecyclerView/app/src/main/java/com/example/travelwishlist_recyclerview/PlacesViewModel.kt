package com.example.travelwishlist_recyclerview

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

    // Example data added for testing. You may modify, or use an empty MutableList.
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