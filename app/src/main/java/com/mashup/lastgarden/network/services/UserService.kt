package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.network.response.NetworkDataResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("user")
    suspend fun signUser(@Body params: RequestBody): NetworkDataResponse<Token>

    @GET("user/{nickname}")
    suspend fun checkDuplicatedUserName(
        @Path("nickname") name: String
    ): NetworkDataResponse<ValidUserNickName>
}