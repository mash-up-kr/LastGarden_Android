package com.mashup.lastgarden.data.remote

import com.mashup.base.extensions.requestBodyOf
import com.mashup.base.extensions.toJsonRequestBody
import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.network.response.NetworkDataResponse
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.UserService
import com.mashup.lastgarden.ui.sign.AuthType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val service: UserService) {

    suspend fun signUser(idToken: String, oAuthType: AuthType): NetworkDataResponse<Token> =
        service.signUser(
            requestBodyOf {
                "idToken" to idToken
                "oAuthType" to oAuthType.name
            }
        )

    suspend fun loginUser(email: String): Token? =
        service.loginUser(email).onErrorReturnDataNull()

    suspend fun checkDuplicatedUserName(name: String): ValidUserNickName? =
        service.checkDuplicatedUserName(name).onErrorReturnDataNull()
}