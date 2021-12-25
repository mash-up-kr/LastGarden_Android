package com.mashup.lastgarden.ui.account

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mashup.base.extensions.combine
import com.mashup.base.extensions.toMultipartBody
import com.mashup.lastgarden.R
import com.mashup.lastgarden.data.repository.PerfumeRepository
import com.mashup.lastgarden.data.repository.UserRepository
import com.mashup.lastgarden.data.vo.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _inputNickname = MutableStateFlow<String?>(null)
    val inputNickname: StateFlow<String?> = _inputNickname

    private val _inputProfileUrl = MutableStateFlow<Uri?>(null)
    val inputProfileUri: StateFlow<Uri?> = _inputProfileUrl

    init {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getUser()?.let {
                _user.emit(it)
                _inputNickname.emit(it.nickname)
            }
        }
    }

    val isChanged: Flow<Boolean> = _user.combine(_inputNickname, _inputProfileUrl)
        .map { (initialUser, nickname, url) ->
            initialUser?.nickname != nickname || url != null
        }

    fun setNickname(nickname: String) {
        viewModelScope.launch {
            _inputNickname.emit(nickname)
        }
    }

    fun setProfileUri(url: Uri?) {
        viewModelScope.launch {
            _inputProfileUrl.emit(url)
        }
    }

    private val _isLoading = MutableStateFlow(false)
    val isLoading: Flow<Boolean> = _isLoading

    private val _isFinished = MutableSharedFlow<Unit>()
    val isFinished: Flow<Unit> = _isFinished

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: Flow<String> = _errorMessage

    fun editProfile(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.emit(true)
            val profileUrl = _inputProfileUrl.value
            val multipart = profileUrl?.toMultipartBody(context, "image")
            if (multipart == null) {
                _isLoading.emit(false)
                _errorMessage.emit(context.getString(R.string.open_file_error))
                return@launch
            }
            val image = perfumeRepository.uploadImage(multipart)
            if (image == null) {
                _isLoading.emit(false)
                _errorMessage.emit(context.getString(R.string.uploading_file_error))
                return@launch
            }
            userRepository.updateProfileImage(image.imageId)
            _isLoading.emit(false)
            _isFinished.emit(Unit)
        }
    }
}