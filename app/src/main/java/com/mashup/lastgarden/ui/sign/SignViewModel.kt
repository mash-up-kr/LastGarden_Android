package com.mashup.lastgarden.ui.sign

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.repository.UserRepository
import com.mashup.lastgarden.data.vo.Token
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: PerfumeSharedPreferences
) : ViewModel() {

    private val _accessToken = MutableLiveData<Token>()
    val accessToken: LiveData<Token>
        get() = _accessToken

    fun requestSignIn(idToken: String, authType: AuthType) = viewModelScope.launch {
        userRepository.signUser(idToken, authType)?.let { token ->
            _accessToken.value = token
            setUserAccessToken(token.seehyangToken)
        }
    }

    private fun setUserAccessToken(accessToken: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveAccessToken(accessToken)
        }
    }
}