package com.obando.test.core.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.obando.test.core.listener.CommonErrorListener
import com.obando.test.core.network.NetworkResult
import com.obando.test.models.enums.ErrorEnum
import org.koin.android.BuildConfig

abstract class BaseFragment : SimpleBaseFragment() {

     /**
     * This abstract method is the place where should be set all observers needed by the view, and
     * then could be called in [onStart].
     */
    abstract fun setObservers()

    /**
     * This abstract method is the place where should be removed all observers previously instantiated
     * in the view. This must be called in [onPause].
     */
    abstract fun removeObservers()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBaseViewComponents()
    }

    fun setErrorHandlersToViewModel(
        viewModel: BaseViewModel
    ) {
        viewModel.setOnCommonErrorListener(object : CommonErrorListener {
            override fun onNoInternetConnection() {
                displayLoadingView(display = false)
            }

            override fun onServerError(failureResponse: NetworkResult.FailureResponse) {
                with(failureResponse) { displayErrorToast(httpCode, errorType, errorMessage) }
                displayLoadingView(display = false)
            }

            override fun onClientError(failureResponse: NetworkResult.FailureResponse) {
                if (failureResponse.httpCode == 401) {
                    onAuthenticationError(failureResponse)
                }
                displayLoadingView(display = false)
            }

            override fun onAuthenticationError(failureResponse: NetworkResult.FailureResponse) {
                displayLoadingView(display = false)
            }
        })
    }

    private fun displayErrorToast(httpCode: Int?, errorType: ErrorEnum?, message: String?) {
        if (BuildConfig.DEBUG) {
            displayToast(
                message = "Http code: ${httpCode ?: "null"}\n" +
                        "ErrorType: ${errorType ?: "null"}\n" +
                        "Message: ${message ?: "null"}",
                duration = Toast.LENGTH_LONG
            )
        } else {
            val toastMessage = when (errorType) {
                ErrorEnum.NO_INTERNET_CONNECTION -> "You are not connected to the internet. Please reconnect to WiFi or cellular data"
                ErrorEnum.CLIENT_ERROR -> "Something went wrong with the application"
                ErrorEnum.SERVER_ERROR -> "Something went wrong with the network"
                ErrorEnum.TOKEN_EXPIRED -> "We were unable to verify your account. Please login in again"
                null -> "Something went wrong"
            }
            displayToast(
                message = toastMessage,
                duration = Toast.LENGTH_LONG
            )
        }
    }

}