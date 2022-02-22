package com.example.travelwishlist_recyclerview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlacesViewModel: ViewModel() {

    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>?>(null)
    val userMessage = MutableLiveData<String>()

    init {
        getPlaces()
    }


    fun getPlaces() {
        viewModelScope.launch(Dispatchers.IO) {
            val apiResult = placeRepository.getAllPlaces()
            if (apiResult.status == ApiStatus.SUCCESS) {
                allPlaces.postValue(apiResult.data)
            }
            userMessage.postValue(apiResult.message)
        }
    }


    fun addNewPlace(place: Place) {
        viewModelScope.launch {
            placeRepository.addPlace(place).also { updateUI(it) }
        }
    }


    fun deletePlace(place: Place) {
        viewModelScope.launch {
            placeRepository.deletePlace(place).also { updateUI(it) }
        }
    }


    fun updatePlace(place: Place) {
        viewModelScope.launch {
            placeRepository.updatePlace(place).also { updateUI(it) }
        }
    }


    private fun updateUI(result: ApiResult<Any>) {
        if (result.status == ApiStatus.SUCCESS) {
            getPlaces()
        }
        userMessage.postValue(result.message)
    }
}



