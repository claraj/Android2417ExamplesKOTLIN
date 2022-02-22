package com.example.travelwishlist_recyclerview

import android.util.Log
import com.example.travelwishlist_recyclerview.place_service.ApiResult
import com.example.travelwishlist_recyclerview.place_service.ApiStatus
import com.example.travelwishlist_recyclerview.place_service.AuthorizationHeaderInterceptor
import com.example.travelwishlist_recyclerview.place_service.PlaceService
import okhttp3.OkHttpClient
import retrofit2.Response
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

    suspend fun <T: Any> apiCall(apiCallFunction: suspend ()-> Response<T>, failMessage: String): ApiResult<T> {
        try {
            val response = apiCallFunction.invoke()
            if (response.isSuccessful) {
                return ApiResult(ApiStatus.SUCCESS, response.body(), null)
            } else {
                return ApiResult(ApiStatus.NOT_SUCCESS, null, failMessage)
            }
        } catch (ex: Exception) {
            return ApiResult(ApiStatus.ERROR, null, "Error connecting to server")
        }
    }

    suspend fun getAllPlaces(): ApiResult<List<Place>> {
        return apiCall(placeService::getAllPlaces, "Error getting places")
    }

    suspend fun addPlace(place: Place): ApiResult<Place> {
        return apiCall( { placeService.addPlace(place) }, "Error adding place ${place.name}")
    }

    suspend fun updatePlace(place: Place): ApiResult<Place> {
        return if (place.id == null) {
            ApiResult(ApiStatus.ERROR, null, "Attempting to update a place with no ID")
        } else {
            apiCall( { placeService.updatePlace(place, place.id) }, "Error updating place ${place.name}")
        }
    }

    suspend fun deletePlace(place: Place): ApiResult<Any> {
        return if (place.id == null) {
            ApiResult(ApiStatus.ERROR, null, "Attempting to delete a place with no ID")
        } else {
           apiCall( { placeService.deletePlace(place.id) }, "Error deleting place ${place.name}")
        }
    }

//  suspend fun getAllPlaces(): ApiResult<List<Place>> {
//        try {
//            val response = placeService.getAllPlaces()
//
//            if (response.isSuccessful) {
//                return ApiResult(ApiStatus.SUCCESS, response.body(), null)
//            } else {
//                return ApiResult(ApiStatus.NOT_SUCCESS, null, "Error getting places")
//            }
//        } catch (ex: Exception) {
//            return ApiResult(ApiStatus.ERROR, null, "Error connecting to server")
//        }
//    }

//    suspend fun addPlace(place: Place): ApiResult<Place> {
//        try {
//            val response = placeService.addPlace(place)
//            if (response.isSuccessful) {
//                return ApiResult(ApiStatus.SUCCESS, response.body(), null)
//            } else {
//                return ApiResult(ApiStatus.NOT_SUCCESS, null, "Error adding ${place.name} - no duplicates!")
//            }
//        } catch (ex: Exception) {
//            return ApiResult(ApiStatus.ERROR, null, "Error connecting to server.")
//        }
//    }

//    suspend fun updatePlace(place: Place): ApiResult<Place> {
//
//        if (place.id == null) {
//            return ApiResult(ApiStatus.ERROR, null, "Attempting to update a place with no ID")
//        } else {
//            try {
//                val response = placeService.updatePlace(place, place.id)
//                if (response.isSuccessful) {
//                    return ApiResult(ApiStatus.SUCCESS, response.body(), null)
//                } else {
//                    return ApiResult(ApiStatus.NOT_SUCCESS, null, "Error updating ${place.name}")
//                }
//            } catch (ex: Exception) {
//                return ApiResult(ApiStatus.ERROR, null, "Error connecting to server.")
//            }
//        }
//    }

//    suspend fun deletePlace(place: Place): ApiResult<Any> {
//        if (place.id == null) {
//            return ApiResult(ApiStatus.ERROR, null, "Attempting to delete a place with no ID")
//        } else {
//            try {
//                val response = placeService.deletePlace(place.id)
//                if (response.isSuccessful) {
//                    return ApiResult(ApiStatus.SUCCESS, null, null)
//                } else {
//                    return ApiResult(ApiStatus.NOT_SUCCESS, null, "Error deleting ${place.name}")
//                }
//            } catch (ex: Exception) {
//                return ApiResult(ApiStatus.ERROR, null, "Error connecting to server.")
//            }
//        }
//    }
}