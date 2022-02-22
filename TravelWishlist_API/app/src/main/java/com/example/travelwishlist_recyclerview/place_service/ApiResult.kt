package com.example.travelwishlist_recyclerview.place_service

enum class ApiStatus {
    SUCCESS, NOT_SUCCESS, ERROR
}

class ApiResult<out T> (val status: ApiStatus, val data: T?, val message: String?)