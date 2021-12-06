package com.mashup.lastgarden.data.remote

import com.mashup.base.extensions.requestBodyOf
import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.network.response.NetworkDataResponse
import com.mashup.lastgarden.network.response.Response
import com.mashup.lastgarden.network.response.onErrorReturnDataNull
import com.mashup.lastgarden.network.services.UserService
import com.mashup.lastgarden.ui.sign.AuthType
import com.mashup.lastgarden.ui.sign.GenderType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(private val service: UserService) {

    suspend fun getUser(): User? = service.getUser().onErrorReturnDataNull()

    suspend fun signUser(idToken: String, oAuthType: AuthType): Response<Token> =
        service.signUser(
            requestBodyOf {
                "idToken" to idToken
                "oAuthType" to oAuthType.name
            }
        )

    suspend fun registerUser(
        age: Int,
        gender: GenderType,
        nickName: String
    ): User? =
        service.registerUser(
            requestBodyOf {
                "age" to age
                "gender" to gender
                "nickname" to nickName
            }
        ).onErrorReturnDataNull()

    suspend fun loginUser(email: String) = service.loginUser(email)

    suspend fun checkDuplicatedUserName(name: String) = service.checkDuplicatedUserName(name)

    suspend fun updateUserProfileImage(imageId: Int) = service.updateUserProfile(
        requestBodyOf { "imageId" to imageId }
    )
}