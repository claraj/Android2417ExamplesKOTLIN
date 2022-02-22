package com.example.travelwishlist_recyclerview

import java.text.SimpleDateFormat
import java.util.*

data class Place(val id: Int, val name: String, val dateAdded: Date = Date(), var starred: Boolean = false) {

    fun formattedDate(): String {
        /** Return date as a formatted string for example "Wed, 4 July 2001" */
        return SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault()).format(dateAdded)
    }
}



