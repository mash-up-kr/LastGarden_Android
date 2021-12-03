package com.mashup.lastgarden.ui.sign

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mashup.base.BaseViewModel
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.repository.UserRepository
import com.mashup.lastgarden.data.vo.User
import com.mashup.lastgarden.network.NetworkCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: PerfumeSharedPreferences
) : BaseViewModel() {
    private var userAge: Int? = null

    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName

    private val _genderType = MutableLiveData<GenderType>()
    val genderType: LiveData<GenderType>
        get() = _genderType

    private val _user = MutableSharedFlow<User>()
    val user: SharedFlow<User>
        get() = _user

    private val _needUserRegister = MutableStateFlow(false)
    val needUserRegister: StateFlow<Boolean>
        get() = _needUserRegister

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
            val result = userRepository.checkValidNickName(name)

            if (result.data?.isDuplicated != false) {
                _snackBarStringResId.emit(R.string.sign_duplicated_name)
            }
            _isValidName.emit(result.data?.isDuplicated == false)
        }
    }

    private fun checkUserInformation() = viewModelScope.launch {
        _isValidUserInformation.emit(userAge != null && _genderType.value != null)
    }

    fun requestLogin(idToken: String, email: String?, authType: AuthType) = viewModelScope.launch {
        val result = userRepository.signUser(idToken, authType)

        when (result.code) {
            NetworkCode.EXIST_USER -> {
                email?.let {
                    val loginToken = userRepository.loginUser(email)
                    loginToken.data?.let { loginAccessToken ->
                        setUserAccessToken(loginAccessToken.token)
                        getUser()
                    }
                }
            }
            null -> {
                result.data?.token?.let { accessToken ->
                    setUserAccessToken(accessToken)
                    getUser()
                }
            }
        }
    }

    fun registerUser() = viewModelScope.launch {
        val user = userRepository.registerUser(
            age = userAge ?: return@launch,
            genderType = genderType.value ?: return@launch,
            nickName = _userName.value ?: return@launch
        )
        user?.let { _user.emit(user) }
        _needUserRegister.emit(user == null)
    }

    private fun getUser() = viewModelScope.launch {
        val user = userRepository.getUser()
        if (user != null && !user.isEmpty()) {
            _user.emit(user)
        }
        _needUserRegister.emit(user == null || user.isEmpty())
    }

    private fun setUserAccessToken(accessToken: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveAccessToken(accessToken)
        }
    }
}