package com.example.travelwishlist_recyclerview.place_service

import com.example.travelwishlist_recyclerview.Place

import retrofit2.Response
import retrofit2.http.GET

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // TODO add a place

    // TODO edit a place

    // TODO delete a place

}


