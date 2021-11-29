package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.network.response.NetworkDataResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @POST("user")
    suspend fun signUser(@Body params: RequestBody): NetworkDataResponse<Token>

    @GET("login")
    suspend fun loginUser(@Query("email") email: String): NetworkDataResponse<Token>

    @GET("user/{nickname}")
    suspend fun checkDuplicatedUserName(
        @Path("nickname") name: String
    ): NetworkDataResponse<ValidUserNickName>
}