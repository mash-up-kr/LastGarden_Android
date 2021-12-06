package com.mashup.lastgarden.data.repository

import com.mashup.lastgarden.data.remote.UserRemoteDataSource
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.network.response.NetworkDataResponse
import com.mashup.lastgarden.ui.sign.AuthType
import com.mashup.lastgarden.ui.sign.GenderType
import javax.inject.Inject

class UserRepository @Inject constructor(private val remote: UserRemoteDataSource) {

    suspend fun getUser() = remote.getUser()

    suspend fun signUser(
        idToken: String,
        oAuthType: AuthType
    ) = remote.signUser(idToken, oAuthType)

    suspend fun loginUser(
        email: String
    ) = remote.loginUser(email)

    suspend fun registerUser(
        age: Int,
        genderType: GenderType,
        nickName: String
    ) = remote.registerUser(age, genderType, nickName)

    suspend fun checkValidNickName(name: String) = remote.checkDuplicatedUserName(name)
}