package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainContainerViewModel @Inject constructor() : ViewModel() {

    private val _mainContainerPosition = MutableSharedFlow<Int>()
    val mainContainerPosition = _mainContainerPosition.asSharedFlow()

    fun setMainContainerPosition(
        containerPagerType: MainContainerFragment.MainContainerPagerType
    ) = viewModelScope.launch {
        _mainContainerPosition.emit(containerPagerType.position)
    }
}