package com.example.tsalinerandomizer_fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArrowViewModel: ViewModel() {

    val arrowDirections = listOf("LEFT", "RIGHT")

    // Using a nullable type because we may not have an arrow direction set,
    // for example, when the app launches, or the display is cleared.
    val currentArrowDirection = MutableLiveData<String?>(null)

    fun clearArrow() {
        currentArrowDirection.value = null
    }
}