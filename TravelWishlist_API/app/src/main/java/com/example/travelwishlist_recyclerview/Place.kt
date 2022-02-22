package com.example.travelwishlist_recyclerview

import java.text.SimpleDateFormat
import java.util.*

data class Place(val name: String, var reason: String? = null, var starred: Boolean = false, val id: Int? = null) {

//    fun formattedDate(): String {
//        /** Return date as a formatted string for example "Wed, 4 July 2001" */
//        return SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault()).format(dateAdded)
//    }
}



