package com.mashup.lastgarden.data.repository

import com.mashup.lastgarden.data.remote.UserRemoteDataSource
import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.ValidUserNickName
import com.mashup.lastgarden.ui.sign.AuthType
import javax.inject.Inject

class UserRepository @Inject constructor(private val remote: UserRemoteDataSource) {

    suspend fun signUser(
        idToken: String,
        oAuthType: AuthType
    ): Token? = remote.signUser(idToken, oAuthType)

    suspend fun checkValidNickName(name: String): ValidUserNickName? =
        remote.checkDuplicatedUserName(name)
}