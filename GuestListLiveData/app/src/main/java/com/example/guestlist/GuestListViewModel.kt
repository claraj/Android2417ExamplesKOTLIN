package com.example.guestlist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuestListViewModel: ViewModel() {

    // Wrap the guestNames list in a MutableLiveData
    val guestNames = MutableLiveData(mutableListOf<String>())

    fun getSortedGuestNames(): List<String> {
        // get the value of the live data, sort, and return
        // If no data, return empty list
        return guestNames.value?.sorted() ?: listOf()
    }

    fun addGuest(name: String) {
        // Get the value of the live data, or an empty list if it is null
        val guests = guestNames.value ?: mutableListOf()
        // add the name,
        guests.add(name)
        // set the value to the updated list. Setting the
        // value causes any observers to be notified.
        guestNames.value = guests
    }

    fun clearGuestList() {
        guestNames.value = mutableListOf()
    }
}

