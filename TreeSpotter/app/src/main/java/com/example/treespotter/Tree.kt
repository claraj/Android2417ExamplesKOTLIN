package com.example.treespotter

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint
import java.util.*

// Have to use default arguments and nullable types
// Since there's no guarantees that a Firebase document will have any of these fields,
// we can't require that a Tree object must contain them
class Tree(val name: String? = null, val dateSpotted: Date? = null, val location: GeoPoint? = null) {

    override fun toString(): String {
        return "$name, $dateSpotted, $location"
    }

    fun latLong(): LatLng? {
        return location?.let { LatLng(it.latitude, location.longitude) }
    }

}

// Could also use a data class

