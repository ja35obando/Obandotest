package com.obando.test.core.repository

import com.obando.test.core.api.HomeApi
import com.obando.test.core.base.BaseRepository
import com.obando.test.core.models.WeatherModel
import com.obando.test.core.network.NetworkResult

class HomeRepository(
    private val homeApi: HomeApi
) : BaseRepository() {

    suspend fun summary() : NetworkResult<WeatherModel?> =
        executeNetworkCall { homeApi.summary() }
}