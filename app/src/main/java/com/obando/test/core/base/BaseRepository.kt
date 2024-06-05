package com.obando.test.core.base

import com.google.gson.GsonBuilder
import com.obando.test.core.network.NetworkResult
import com.obando.test.models.enums.ErrorEnum
import com.obando.test.models.enums.ErrorEnum.CLIENT_ERROR
import com.obando.test.models.enums.ErrorEnum.NO_INTERNET_CONNECTION
import com.obando.test.models.enums.ErrorEnum.SERVER_ERROR
import com.obando.test.models.enums.ErrorEnum.TOKEN_EXPIRED
import com.obando.test.utlis.NetworkUtil
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response

open class BaseRepository : KoinComponent {

    private val networkUtil: NetworkUtil by inject()
    private val clientErrors = 400..499
    private val serverErrors = 500..599
    private val tokenExpired = 403

    /**
     * This method is used to execute the network call to the retrofit endpoint passed as parameter.
     *
     * @param retrofitEndPointCall Endpoint defined in API interface.
     * @return [NetworkResult] Response model wrapped inside of a network result successful or failed.
     */
    suspend fun <T> executeNetworkCall(
        retrofitEndPointCall: suspend () -> Response<T>
    ): NetworkResult<T?> =
        // Verify internet connection established.
        if (networkUtil.hasInternetConnection()) {
            try {
                // Make call using Retrofit, then handle the received response.
                handleNetworkResponse(retrofitEndPointCall())
            } catch (exception: Exception) {
                // Response models are not accurate, expected parsing errors.
                NetworkResult.FailureResponse(errorMessage = exception.message)
            }
        } else {
            // Create a failure response to handle this scenario.
            NetworkResult.FailureResponse(errorType = NO_INTERNET_CONNECTION)
        }

    /**
     * Method responsible for create a [NetworkResult] object with the https [Response] received.
     * @param httpsResponse Response object from OkHttp3 library.
     * @return [NetworkResult] Retrieve the response received wrapped by a networkResult object.
     */
    private fun <T> handleNetworkResponse(
        httpsResponse: Response<T>
    ): NetworkResult<T?> {
        with(httpsResponse) {
            return if (isSuccessful) {
                NetworkResult.SuccessResponse(responseObject = body(), rawResponse = raw())
            } else {
                val gson = GsonBuilder().create()
                NetworkResult.FailureResponse(
                    errorMessage = raw().message,
                    httpCode = code(),
                    errorType = getErrorType(code()),
                    errorBody = errorBody()?.string()
                )
            }
        }
    }

    /**
     * Method responsible for identifies the error type to make easier handle this error by the
     * BaseViewModel, and BaseFragment.
     */
    private fun getErrorType(httpCode: Int): ErrorEnum? {
        return when {
            tokenExpired == httpCode -> {
                TOKEN_EXPIRED
            }
            serverErrors.contains(httpCode) -> {
                SERVER_ERROR
            }
            clientErrors.contains(httpCode) -> {
                CLIENT_ERROR
            }
            else -> null
        }
    }
}