package com.mashup.lastgarden.ui.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _tagList = MutableLiveData<Set<String>>()
    val tagList: LiveData<Set<String>> = _tagList

    private val _isEnabledUploadButton = MutableLiveData<Boolean>()
    val isEnabledUploadButton: LiveData<Boolean> = _isEnabledUploadButton

    fun setTagList(tagList: List<String>) {
        _tagList.value = tagList.toSet()
    }
}