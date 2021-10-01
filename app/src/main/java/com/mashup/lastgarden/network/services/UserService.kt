package com.mashup.lastgarden.network.services

import retrofit2.http.GET

interface UserService {

    /** This is sample code for understanding teammates.
     * Please remove below codes when defining UserService
     */
    @GET("user")
    suspend fun getUser(userId: String): Any
}