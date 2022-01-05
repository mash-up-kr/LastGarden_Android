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
import com.mashup.lastgarden.data.vo.Story
import com.mashup.lastgarden.ui.upload.perfume.PerfumeItem
import com.mashup.lastgarden.ui.upload.perfume.PerfumeSelectedItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UploadViewModel @Inject constructor(
    private val perfumeRepository: PerfumeRepository,
    private val storyRepository: StoryRepository
) : ViewModel() {

    sealed class UploadState {
        data class Success(val story: Story) : UploadState()
        object Failure : UploadState()
        object None : UploadState()
    }

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


    private val _queryOfPerfume = MutableStateFlow("")
    val queryOfPerfume: StateFlow<String>
        get() = _queryOfPerfume

    private val _selectedPerfume = MutableStateFlow<PerfumeItem>(PerfumeItem.EmptyPerfume)
    val selectedPerfume: StateFlow<PerfumeItem> = _selectedPerfume

    private val _onStorySaveSuccess = MutableSharedFlow<UploadState>()
    val onStorySaveSuccess = _onStorySaveSuccess.asSharedFlow()


    val searchedPerfumeList by lazy {
        queryOfPerfume.flatMapLatest { query ->
            if (query.isNotBlank()) {
                perfumeRepository.getPerfumesWithName(PAGE_SIZE, query)
            } else {
                flowOf()
            }
        }.cachedIn(viewModelScope)
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

    fun requestPerfumeWithName(name: String) = viewModelScope.launch {
        _queryOfPerfume.emit(name)
    }

    fun updateSelectedPerfume(perfumeSelectedItem: PerfumeItem.PerfumeSearchedItem) =
        viewModelScope.launch {
            _selectedPerfume.emit(perfumeSelectedItem)
        }

    fun uploadStory() = viewModelScope.launch {
        val editedImage = editedImage.value
        val savedImage = perfumeRepository.uploadImage(
            key = KEY_IMAGE_FILE,
            image = editedImage ?: return@launch
        )
        savedImage?.let {
            val story = storyRepository.uploadStory(
                imageId = savedImage.imageId,
                perfumeId = (selectedPerfume.value as? PerfumeSelectedItem)?.id,
                tags = tagSet.value?.toList() ?: emptyList()
            )
            _onStorySaveSuccess.emit(
                if (story != null) {
                    UploadState.Success(story)
                } else {
                    UploadState.Failure
                }
            )
        }
    }
}