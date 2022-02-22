package com.example.travelwishlist_recyclerview

// status of request - success, server error, network error
// data, if there is any
// message for user, if needed

enum class ApiStatus {
    SUCCESS,
    SERVER_ERROR,
    NETWORK_ERROR
}

class ApiResult<out T> (val status: ApiStatus, val data: T?, val message: String?)