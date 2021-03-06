package com.mashup.lastgarden.ui

import androidx.lifecycle.viewModelScope
import com.mashup.base.BaseViewModel
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import com.mashup.lastgarden.data.repository.UserRepository
import com.mashup.lastgarden.data.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: PerfumeSharedPreferences
) : BaseViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    val name: Flow<String?> = user.map { it?.nickname }

    val profileImage: Flow<String?> = user.map { it?.profileImage }

    fun resetUser() {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveAccessToken(null)
        }
    }

    fun refresh() {
        fetchMe()
    }

    private fun fetchMe() {
        viewModelScope.launch(Dispatchers.IO) {
            val token = sharedPreferences.getAccessToken()
            if (token == null) {
                _needUserToken.emit(true)
                return@launch
            }

            userRepository.getUser()?.let { _user.emit(it) }
        }
    }
}