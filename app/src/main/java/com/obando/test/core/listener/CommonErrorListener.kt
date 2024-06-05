package com.obando.test.core.listener

import com.obando.test.core.network.NetworkResult

interface CommonErrorListener {

    /**
     * Internal error when internet connection is not detected
     */
    fun onNoInternetConnection()

    /**
     * HTTP errors from 500 to 599
     */
    fun onServerError(failureResponse: NetworkResult.FailureResponse)

    /**
     * HTTP errors from 400 to 499 aside from 403
     */
    fun onClientError(failureResponse: NetworkResult.FailureResponse)

    /**
     * HTTP errors equal to 403
     */
    fun onAuthenticationError(failureResponse: NetworkResult.FailureResponse)

}