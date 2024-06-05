package com.obando.test.core.network

import com.google.gson.Gson
import com.obando.test.core.api.HomeApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.factory
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {

    // Base resources
    factory { NetworkInterceptor() }
    factory { provideOkHttpClient(get()) }
    single { GsonConverterFactory.create(Gson()) as Converter.Factory }
    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    factory {provideHomeApi(getRetrofit("https://api.open-meteo.com/v1/", get(), get())) }
}

private val loggingInterceptor = HttpLoggingInterceptor().apply { level =
    HttpLoggingInterceptor.Level.BODY
}

fun provideOkHttpClient(networkInterceptor: NetworkInterceptor): OkHttpClient =
    with(OkHttpClient().newBuilder()) {
        connectTimeout(10, TimeUnit.SECONDS)
        writeTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
        addInterceptor(networkInterceptor)
        addInterceptor(loggingInterceptor)
        build()
    }

fun getRetrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    factory: Converter.Factory
): Retrofit = with(Retrofit.Builder()) {
    baseUrl(baseUrl)
    client(okHttpClient)
    addConverterFactory(factory)
    build()
}

fun provideHomeApi(retrofit: Retrofit): HomeApi =
    retrofit.create(HomeApi::class.java)