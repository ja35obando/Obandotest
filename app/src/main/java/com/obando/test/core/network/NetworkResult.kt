package com.obando.test.core.network

import com.obando.test.models.enums.ErrorEnum

sealed class NetworkResult<out T> {
    data class SuccessResponse<out T>(val responseObject: T, val rawResponse: okhttp3.Response) : NetworkResult<T?>()
    data class FailureResponse(
        val errorMessage: String? = null,
        val httpCode: Int? = null,
        val errorType: ErrorEnum? = null,
        val errorBody: String? = null
    ) : NetworkResult<Nothing>()
}
