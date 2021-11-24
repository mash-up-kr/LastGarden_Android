package com.mashup.lastgarden.ui.upload

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.lastgarden.Constant.KEY_IMAGE_FILE
import com.mashup.lastgarden.data.repository.PerfumeRepository
import com.mashup.lastgarden.data.repository.StoryRepository
import com.mashup.lastgarden.ui.upload.perfume.PerfumeItem
import com.mashup.lastgarden.ui.upload.perfume.PerfumeSelectedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    companion object {
        private const val MAX_TAG_SIZE = 10
        private const val PAGE_SIZE = 10
    }

    private val _editedImage = MutableLiveData<Bitmap>()
    val editedImage: LiveData<Bitmap> = _editedImage

    private val _tagSet = MutableLiveData<Set<String>>()
    val tagSet: LiveData<Set<String>>
        get() = _tagSet

    private val _isEnabledUploadButton = MutableLiveData<Boolean>()
    val isEnabledUploadButton: LiveData<Boolean>
        get() = _isEnabledUploadButton

    private val _queryOfPerfume = MutableLiveData<String>()
    val queryOfPerfume: LiveData<String>
        get() = _queryOfPerfume

    private val _selectedPerfume =
        MutableSharedFlow<PerfumeItem>(
            replay = 0,
            extraBufferCapacity = 1,
            onBufferOverflow = BufferOverflow.DROP_OLDEST
        )
    val selectedPerfume: SharedFlow<PerfumeItem> = _selectedPerfume

    private val _onStorySaveSuccess = MutableStateFlow(false)
    val onStorySaveSuccess: StateFlow<Boolean> = _onStorySaveSuccess

    init {
        viewModelScope.launch {
            _selectedPerfume.emit(PerfumeItem.EmptyPerfume)
        }
    }

    fun addTag(newTag: String) {
        if (_tagSet.value?.contains(newTag) == true ||
            (_tagSet.value?.size ?: 0) >= MAX_TAG_SIZE
        ) return
        _tagSet.value = tagSet.value?.toMutableSet()?.apply {
            add(newTag)
        } ?: setOf(newTag)
    }

    fun removeTag(removeTag: String) {
        if (_tagSet.value?.contains(removeTag) != true) return
        _tagSet.value = tagSet.value?.toMutableSet()?.apply {
            remove(removeTag)
        }
    }

    fun setEditedImage(image: Bitmap) {
        editedImage.value?.recycle()
        _editedImage.value = image
    }

    fun requestPerfumeWithName(name: String) = perfumeRepository
        .getPerfumesWithName(PAGE_SIZE, name)
        .cachedIn(viewModelScope)
        .combine(selectedPerfume) { pagingData, selectedItem ->
            pagingData.map { perfume ->
                val isSelected = if (selectedItem is PerfumeItem.PerfumeSearchedItem) {
                    perfume.perfumeId == selectedItem.id
                } else {
                    false
                }

                PerfumeSelectedItem(
                    id = perfume.perfumeId,
                    imageUrl = perfume.thumbnailUrl,
                    brandName = perfume.brandName,
                    name = perfume.koreanName,
                    likeCount = perfume.likeCount ?: 0,
                    isSelected = isSelected
                )
            }
        }

    fun updateSelectedPerfume(perfumeSelectedItem: PerfumeItem.PerfumeSearchedItem) =
        viewModelScope.launch {
            _selectedPerfume.emit(perfumeSelectedItem)
        }

    fun uploadStory() = viewModelScope.launch {
        val savedImage = savaImage()
        savedImage?.let {
            val story = storyRepository.uploadStory(
                imageId = savedImage.imageId,
                perfumeId = (selectedPerfume.lastOrNull() as? PerfumeSelectedItem)?.id,
                tags = tagSet.value?.toList() ?: emptyList()
            )
            _onStorySaveSuccess.emit(story != null)
        }
    }

    private suspend fun savaImage() = withContext(Dispatchers.IO) {
        val editedImage = editedImage.value

        if (editedImage != null) {
            return@withContext perfumeRepository.uploadImage(KEY_IMAGE_FILE, editedImage)
        } else {
            return@withContext null
        }
    }
}