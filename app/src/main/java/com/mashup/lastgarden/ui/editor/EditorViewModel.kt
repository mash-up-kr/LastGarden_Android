package com.mashup.lastgarden.ui.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mashup.lastgarden.ui.editor.EditorActivity.Companion.EXTRA_IMAGE_URL
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val imageUrl
        get() = savedStateHandle.getLiveData<String>(EXTRA_IMAGE_URL)
}