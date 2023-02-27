package com.example.tsalinerandomizer_fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArrowViewModel: ViewModel() {
    // Using a nullable type because we may not have an arrow direction set,
    // when the app launches, or the display is cleared
    val currentArrowDirection = MutableLiveData<String?>(null)

    val arrowDirections = listOf("LEFT", "RIGHT")

    fun clearArrow() {
        currentArrowDirection.value = null
    }
}