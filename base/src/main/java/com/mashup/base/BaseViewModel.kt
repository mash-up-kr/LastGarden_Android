package com.mashup.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _snackBarStringResId = MutableSharedFlow<Int>()
    val snackBarStringResId = _snackBarStringResId.asSharedFlow()

    protected val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    protected val _needUserToken = MutableSharedFlow<Boolean>()
    val needUserToken = _needUserToken.asSharedFlow()
}