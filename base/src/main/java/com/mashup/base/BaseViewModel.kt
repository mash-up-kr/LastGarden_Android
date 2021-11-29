package com.mashup.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel : ViewModel() {

    protected val _snackBarStringResId = MutableStateFlow<Int?>(null)
    val snackBarStringResId: StateFlow<Int?>
        get() = _snackBarStringResId

    protected val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean>
        get() = isLoading
}