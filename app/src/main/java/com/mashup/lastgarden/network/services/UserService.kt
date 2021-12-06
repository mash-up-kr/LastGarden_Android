package com.mashup.lastgarden.network.services

import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.network.response.NetworkDataResponse
import com.mashup.lastgarden.network.response.Response
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UserService {

    @GET("user")
    suspend fun getUser(): NetworkDataResponse<User>

    @POST("user")
    suspend fun signUser(@Body params: RequestBody): Response<Token>

    @GET("login")
    suspend fun loginUser(@Query("email") email: String): Response<Token>

    @GET("user/{nickname}")
    suspend fun checkDuplicatedUserName(
        @Path("nickname") name: String
    ): Response<ValidUserNickName>

    @PUT("user")
    suspend fun registerUser(@Body params: RequestBody): NetworkDataResponse<User>
}