package com.example.travelwishlist_recyclerview

import android.util.Log
import com.example.travelwishlist_recyclerview.place_service.AuthorizationHeaderInterceptor
import com.example.travelwishlist_recyclerview.place_service.PlaceService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "PLACE_REPOSITORY"

class PlaceRepository {

    private val BASE_URL = "https://place-wish-list.herokuapp.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun getAllPlaces(): List<Place> {
        val response = placeService.getAllPlaces()
        if (response.isSuccessful) {
            val places = response.body() ?: listOf()
            Log.d(TAG, "Places from API: $places")
            return places
        } else {
            Log.e(TAG, "Error getting all places: ${response.errorBody()}")
            // TODO better error handling - notify user request has failed
            return listOf()
        }
    }
}


/*    suspend fun getAllPlaces(): List<Place> {
       // try {
        val places = placeService.getAllPlaces()
            Log.d("PLACE_REPOSITORY", "$places")
        if (places.isSuccessful) {
            return places.body() ?: listOf()
        } else {
            Log.e("PLACE_REPOSITORY", "ERROR ${places.errorBody()}")
            return listOf()
        }
    }*/