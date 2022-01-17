package com.example.travelwishlist_recyclerview

import androidx.lifecycle.ViewModel

class PlacesViewModel: ViewModel() {

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
        position?.let {  pos ->  // let lambda is called if position is not null. Within the lambda, position is Int not Int?
            placeNames.add(pos, place)
            return position
        }.run {  // run lambda is called if position is null.
            placeNames.add(place)
            return placeNames.size - 1
        }
         */


        // Could also say,
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

        // if the place is moving up the list - from is greater than to -
        // or, if it is staying in the same position,
        // add the place in the to position.
        if (from >= to) {
            placeNames.add(to, place)
        }
        // if the place is moving down the list, for example,
        // moving from element 1 (b) to 3 (between c and d)
        //  a b c d e
        // When element 1 is removed, the list is now
        // a c d e
        //  the target location is 2, so must subtract 1 from to when adding.
        else {
            placeNames.add(to-1, place)
        }
    }

    fun placeAt(position: Int): Place {
        return placeNames[position]
    }
}