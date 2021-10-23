package com.mashup.lastgarden.ui.upload.editor

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _editedImage = MutableLiveData<Bitmap>()
    val editedImage: LiveData<Bitmap> = _editedImage

    private val _tagList = MutableLiveData<Set<String>>()
    val tagList: LiveData<Set<String>> = _tagList

    private val _isEnabledUploadButton = MutableLiveData<Boolean>()
    val isEnabledUploadButton: LiveData<Boolean> = _isEnabledUploadButton

    fun setTagList(tagList: List<String>) {
        _tagList.value = tagList.toSet()
    }

    fun setEditedImage(image: Bitmap) {
        editedImage.value?.recycle()
        _editedImage.value = image
    }
}