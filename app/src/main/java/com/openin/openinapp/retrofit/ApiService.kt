package com.openin.openinapp.retrofit

import com.openin.openinapp.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("api/v1/dashboardNew")
    suspend fun getLinks(
        @Header("Authorization") authToken: String
    ): ApiResponse
}