package com.example.guestlist

import androidx.lifecycle.ViewModel

class GuestListViewModel: ViewModel() {

    val guestNames: MutableList<String> = mutableListOf()

    fun getSortedGuestNames(): List<String> {
        return guestNames.sorted()
    }

    fun addGuest(name: String) {
        guestNames.add(name)
    }

    fun clearGuestList() {
        guestNames.clear()
    }
}

