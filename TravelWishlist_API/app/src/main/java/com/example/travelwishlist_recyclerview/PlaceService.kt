package com.example.travelwishlist_recyclerview

import com.example.travelwishlist_recyclerview.Place

import retrofit2.Response
import retrofit2.http.*

interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // Create a place
    @POST("places/")
    suspend fun addPlace(@Body place: Place): Response<Place>

    // edit a place
    @PATCH("places/{id}/")
    suspend fun updatePlace(@Body place: Place, @Path("id") id: Int): Response<Place>

    @DELETE("places/{id}/")
    suspend fun deletePlace(@Path("id") id: Int): Response<String>

}



