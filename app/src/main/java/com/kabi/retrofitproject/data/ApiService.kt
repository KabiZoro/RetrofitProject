package com.kabi.retrofitproject.data

import com.kabi.retrofitproject.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("latest")
    suspend fun getLatest(
//        @Query("language") language: String = "us",
        @Query("apikey") apikey: String
    ) : NewsResponse
}