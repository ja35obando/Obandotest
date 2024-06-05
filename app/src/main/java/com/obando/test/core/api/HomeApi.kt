package com.obando.test.core.api

import com.obando.test.core.models.WeatherModel
import retrofit2.Response
import retrofit2.http.GET

interface HomeApi {

    @GET("forecast?latitude=34.0522&longitude=-118.2437&current=temperature_2m,relative_humidity_2m&daily=temperature_2m_max&timezone=America%2FLos_Angeles")
    suspend fun summary(): Response<WeatherModel>

}