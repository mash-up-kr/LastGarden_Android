package com.mashup.lastgarden.ui.upload.editor

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditorViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val MAX_TAG_SIZE = 10
    }

    private val _editedImage = MutableLiveData<Bitmap>()
    val editedImage: LiveData<Bitmap> = _editedImage

    private val _tagList = MutableLiveData<Set<String>>()
    val tagList: LiveData<Set<String>> = _tagList

    private val _isEnabledUploadButton = MutableLiveData<Boolean>()
    val isEnabledUploadButton: LiveData<Boolean> = _isEnabledUploadButton

    fun addTag(newTag: String) {
        if (_tagList.value?.contains(newTag) == true ||
            _tagList.value?.size ?: 0 >= MAX_TAG_SIZE
        ) return
        _tagList.value = tagList.value?.toMutableSet()?.apply {
            add(newTag)
        } ?: setOf(newTag)
    }

    fun removeTag(removeTag: String) {
        if (_tagList.value?.contains(removeTag) != true) return
        _tagList.value = tagList.value?.toMutableSet()?.apply {
            remove(removeTag)
        }
    }

    fun setEditedImage(image: Bitmap) {
        editedImage.value?.recycle()
        _editedImage.value = image
    }
}