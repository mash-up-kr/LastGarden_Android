package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.network.response.NetworkDataResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {

    @POST("user")
    suspend fun signUser(@Body params: RequestBody): NetworkDataResponse<Token>
}