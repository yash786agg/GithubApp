package com.app.network

import com.app.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

fun createNetworkClient(baseUrl: String) = retrofitClient(baseUrl, httpClient())

private fun httpClient(): OkHttpClient =
         OkHttpClient.Builder()
        .connectTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        .readTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS)
        .writeTimeout(Constants.TIMEOUT_REQUEST, TimeUnit.SECONDS).build()

private fun retrofitClient(baseUrl: String, httpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(MoshiConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
