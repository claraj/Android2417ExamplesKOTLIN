package com.example.travelwishlist_recyclerview

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val TAG = "PLACE_REPOSITORY"

class PlaceRepository {

    private val BASE_URL = "https://claraj.pythonanywhere.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val placeService = retrofit.create(PlaceService::class.java)

    private suspend fun <T: Any> apiCall(apiCallFunction: suspend () -> Response<T>, successMessage: String?, failMessage: String?): ApiResult<T> {
        try {
            val response = apiCallFunction()   // adding () is equivalent to calling .invoke()
            // val response = apiCallFunction.invoke()
            if (response.isSuccessful) {  // connected, got data back
                Log.d(TAG, "Response body ${response.body()}")
                return ApiResult(ApiStatus.SUCCESS, response.body(), successMessage)
            }
            else {    // connected to server but server sent an error message
                Log.e(TAG, "Server error ${response.errorBody()} ")
                return ApiResult(ApiStatus.SERVER_ERROR, null, failMessage)
            }
        } catch (ex: Exception) {  // can't connect to server - network error
            Log.e(TAG, "Error connecting to API server", ex)
            return ApiResult(ApiStatus.NETWORK_ERROR, null, "Can't connect to server")
        }
    }


    suspend fun getAllPlaces(): ApiResult<List<Place>> {
        return apiCall(placeService::getAllPlaces, null, "Error getting places")
    }


    suspend fun addPlace(place: Place): ApiResult<Place> {
        return apiCall( { placeService.addPlace(place) }, "Place created!", "Error adding place ${place.name}")
    }


    suspend fun updatePlace(place: Place): ApiResult<Place> {
        return if (place.id == null) {
            ApiResult(ApiStatus.SERVER_ERROR, null, "Attempting to update a place with no ID")
        } else {
            apiCall( { placeService.updatePlace(place, place.id) }, "Place updated!","Error updating place ${place.name}")
        }
    }


    suspend fun deletePlace(place: Place): ApiResult<Any> {
        return if (place.id == null) {
            ApiResult(ApiStatus.SERVER_ERROR, null, "Attempting to delete a place with no ID")
        } else {
           apiCall( { placeService.deletePlace(place.id) }, "Place deleted", "Error deleting place ${place.name}")
        }
    }


    // For reference - the original versions

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