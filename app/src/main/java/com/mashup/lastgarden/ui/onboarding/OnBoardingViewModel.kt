package com.mashup.lastgarden.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mashup.lastgarden.data.PerfumeSharedPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val sharedPreferences: PerfumeSharedPreferences
) : ViewModel() {

    val isShowOnBoarding = liveData {
        emit(sharedPreferences.getIsShowOnBoarding())
    }

    fun setIsShowOnBoarding(isShow: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            sharedPreferences.saveIsShowOnBoarding(isShow)
        }
    }
}