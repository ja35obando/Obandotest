package com.obando.test.core.network

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //Added token to each request
        val request = chain.request().newBuilder()
            .header("UserAgent", System.getProperty("http.agent")!!)
            .addHeader("Content-Type", "application/json")
            .build()
        return chain.proceed(request)
    }

}