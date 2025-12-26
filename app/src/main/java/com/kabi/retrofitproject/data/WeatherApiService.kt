package com.kabi.retrofitproject.data

import com.kabi.retrofitproject.domain.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("current.json")
    suspend fun getWeather(
        @Query("key") apikey: String,
        @Query("q") city: String
    ): WeatherResponse
}