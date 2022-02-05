package com.example.maps_and_location

import android.location.Location
import com.google.android.gms.maps.model.LatLng
import java.util.*

open class Attraction(val id: Int, val name: String, val latitude: Double, val longitude: Double, val imageResourceId: Int) {

    val location = LatLng(
          latitude,
         longitude
    )

    var visited = false   // Assume user has not spotted this yet
        set(value) {
            field = value // this sets the value
            if (visited) {
                dateVisited = Date()
            }
            else {
                dateVisited = null
            }
        }

    var dateVisited: Date? = null
        get() {
            // todo whatever you need to do before returning the value
            return field
        }
        set(value) {
            // todo whatever you need to do before the field is set to the new value
            field = value
            // todo whatever you need to do after the field is set to the new value
        }


    override fun toString(): String {
        return "$name, spotted $visited"
    }
}




