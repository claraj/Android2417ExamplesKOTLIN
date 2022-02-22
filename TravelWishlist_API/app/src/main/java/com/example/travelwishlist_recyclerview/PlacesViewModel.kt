package com.example.travelwishlist_recyclerview

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelwishlist_recyclerview.place_service.ApiStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel: ViewModel() {

    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>?>(null)

    val placesError = MutableLiveData<String?>(null)

    init {
        getPlaces()
    }


    fun getPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = placeRepository.getAllPlaces()
            when (result.status) {
                ApiStatus.SUCCESS -> allPlaces.postValue(result.data)
                else -> placesError.postValue(result.message)
            }
        }
    }


    fun addNewPlace(place: Place) {
        viewModelScope.launch {
            val result = placeRepository.addPlace(place)
            when (result.status) {
                ApiStatus.SUCCESS -> getPlaces()
                else -> placesError.postValue(result.message)
            }
        }
    }


    fun deletePlace(place: Place) {
        viewModelScope.launch {
            val result = placeRepository.deletePlace(place)
            when (result.status) {
                ApiStatus.SUCCESS -> getPlaces()
                else -> placesError.postValue(result.message)
            }
        }
    }


    fun updatePlace(place: Place) {
        viewModelScope.launch {
            val result = placeRepository.updatePlace(place)
            when (result.status) {
                ApiStatus.SUCCESS -> getPlaces()
                else -> placesError.postValue(result.message)
            }
        }
    }
}


