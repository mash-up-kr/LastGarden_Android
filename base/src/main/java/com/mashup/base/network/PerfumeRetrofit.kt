package com.mashup.base.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PerfumeRetrofit {

    const val BASE_URL = "127.0.0.1"

    fun <T> create(
        service: Class<T>,
        client: OkHttpClient,
        httpUrl: String = BASE_URL
    ): T = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    inline fun <reified T : Any> create(
        client: OkHttpClient,
        httpUrl: String = BASE_URL
    ): T {
        require(httpUrl.isNotBlank()) { "Parameter httpUrl cannot be blank." }
        return create(service = T::class.java, client = client, httpUrl = httpUrl)
    }
}