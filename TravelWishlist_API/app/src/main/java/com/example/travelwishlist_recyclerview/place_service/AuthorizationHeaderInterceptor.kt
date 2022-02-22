package com.example.travelwishlist_recyclerview.place_service

import com.example.travelwishlist_recyclerview.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationHeaderInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeaders = chain.request().newBuilder()
            .addHeader("Authorization", "Token ${BuildConfig.PLACES_TOKEN}")
            .build()

        return chain.proceed(requestWithHeaders)
    }
}

