package com.mashup.lastgarden.ui.sign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mashup.base.BaseViewModel
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.repository.UserRepository
import com.mashup.lastgarden.data.vo.Token
import com.mashup.lastgarden.data.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: PerfumeSharedPreferences
) : BaseViewModel() {
    private var userAge: Int? = null

    private val _createdUser = MutableLiveData<User>()
    val createdUser: LiveData<User>
        get() = _createdUser

    private val _accessToken = MutableLiveData<Token>()
    val accessToken: LiveData<Token>
        get() = _accessToken

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _genderType = MutableLiveData<GenderType>()
    val genderType: LiveData<GenderType>
        get() = _genderType

    private val _isValidName = MutableStateFlow(false)
    val isValidName: StateFlow<Boolean>
        get() = _isValidName

    private val _isValidUserInformation = MutableStateFlow(false)
    val isValidUserInformation: StateFlow<Boolean>
        get() = _isValidUserInformation

    fun setUserName(name: String) {
        _userName.value = name
    }

    fun setUserAgeByString(ageString: String) {
        try {
            userAge = ageString.toInt()
            checkUserInformation()
        } catch (e: NumberFormatException) {
            Log.e(SignViewModel::class.java.simpleName, e.stackTraceToString())
        }
    }

    fun setGenderType(type: GenderType) {
        _genderType.value = type
        checkUserInformation()
    }

    fun checkDuplicatedUserName() = viewModelScope.launch {
        userName.value?.let { name ->
            val validNickName = userRepository.checkValidNickName(name)

            if (validNickName?.isDuplicated == true) {
                _snackBarStringResId.value = R.string.sign_duplicated_name
            }
            _isValidName.value = validNickName?.isDuplicated == false
        }
    }

    private fun checkUserInformation() {
        _isValidUserInformation.value = userAge != null && _genderType.value != null
    }

    fun requestSignIn(idToken: String, authType: AuthType) = viewModelScope.launch {
        userRepository.signUser(idToken, authType)?.let { token ->
            _accessToken.value = token
            setUserAccessToken(token.token)
        }
    }

    private fun setUserAccessToken(accessToken: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveAccessToken(accessToken)
        }
    }
}