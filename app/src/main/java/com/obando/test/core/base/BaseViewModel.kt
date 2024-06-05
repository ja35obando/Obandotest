package com.obando.test.core.base

import androidx.lifecycle.ViewModel
import com.obando.test.core.listener.CommonErrorListener
import com.obando.test.core.network.NetworkResult
import com.obando.test.models.enums.ErrorEnum

open class BaseViewModel : ViewModel() {

    private var commonErrorListener: CommonErrorListener? = null

    fun validateCommonsErrors(failureResponse: NetworkResult.FailureResponse) {
        failureResponse.errorType?.let {
            when (it) {
                ErrorEnum.CLIENT_ERROR -> commonErrorListener?.onClientError(failureResponse)
                ErrorEnum.NO_INTERNET_CONNECTION -> commonErrorListener?.onNoInternetConnection()
                ErrorEnum.SERVER_ERROR -> commonErrorListener?.onServerError(failureResponse)
                ErrorEnum.TOKEN_EXPIRED -> commonErrorListener?.onAuthenticationError(failureResponse)
            }
        }
    }

    fun setOnCommonErrorListener(onCommonErrorListener: CommonErrorListener) {
        commonErrorListener = onCommonErrorListener
    }
}