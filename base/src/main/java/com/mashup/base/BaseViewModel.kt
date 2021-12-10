package com.mashup.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow

abstract class BaseViewModel : ViewModel() {

    protected val _snackBarStringResId = MutableSharedFlow<Int>()
    val snackBarStringResId: SharedFlow<Int> = _snackBarStringResId.asSharedFlow()

    protected val _isLoading = MutableSharedFlow<Boolean>()
    val isLoading: SharedFlow<Boolean> = _isLoading.asSharedFlow()
}