package com.mashup.lastgarden.network.interceptors

import com.mashup.lastgarden.data.PerfumeSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptor @Inject constructor(
    private val preferences: PerfumeSharedPreferences,
) : Interceptor {
    companion object {
        //TODO 서버에서 알려주는대로 바꿔야함
        private const val KEY_AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
        chain.request()
            .newBuilder()
            .apply {
                runBlocking(Dispatchers.IO) {
                    val token = preferences.getAccessToken() ?: return@runBlocking
                    header(KEY_AUTHORIZATION, token)
                }
            }
            .build()
    )
}