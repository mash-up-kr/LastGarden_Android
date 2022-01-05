package com.mashup.lastgarden.ui.main

import android.text.TextUtils
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.lastgarden.data.repository.PerfumeDetailRepository
import com.mashup.lastgarden.data.vo.Note
import com.mashup.lastgarden.data.vo.Perfume
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PerfumeDetailViewModel @Inject constructor(
    private val perfumeDetailRepository: PerfumeDetailRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    private val _perfumeId = savedStateHandle.getLiveData<Int>("perfumeId")

    private val _perfumeDetailItem = MutableStateFlow<Perfume?>(null)
    val perfumeDetailItem: StateFlow<Perfume?> = _perfumeDetailItem

    private val _selectedNote = MutableStateFlow<PerfumeDetailNote?>(null)
    val selectedNote: StateFlow<PerfumeDetailNote?> = _selectedNote
    val selectedNoteValue: PerfumeDetailNote? get() = _selectedNote.value

    private val _noteContents = MutableStateFlow<String?>(null)
    val noteContents: StateFlow<String?> = _noteContents

    private val _isLiked = MutableStateFlow<Boolean?>(null)
    val isLiked: StateFlow<Boolean?> = _isLiked

    private val _likeCount = MutableStateFlow<Int?>(null)
    val likeCount: StateFlow<Int?> = _likeCount

    private val _storyCount = MutableStateFlow<Int>(0)
    val storyCount: StateFlow<Int> = _storyCount

    fun getStoryItems(): Flow<PagingData<PerfumeDetailItem>> {
        val perfumeId = _perfumeId.value ?: 1
        return perfumeDetailRepository
            .getStoryByPerfume(
                perfumeId, PAGE_SIZE
            )
            .map { pagingData -> pagingData.map { story -> story.toPerfumeDetailStoryItem() } }
            .cachedIn(viewModelScope)
    }

    fun fetchPerfumeDetail() {
        val perfumeId = _perfumeId.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _perfumeDetailItem.value =
                perfumeDetailRepository.fetchPerfumeDetail(perfumeId)
        }
    }

    fun fetchStoryCount() {
        val perfumeId = _perfumeId.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            _storyCount.value = perfumeDetailRepository.getStoryCount(perfumeId)
        }
    }

    fun initSelectedNote(perfumeItem: Perfume) {
        if (perfumeItem.notes?.default.isNullOrEmpty()) {
            setSelectedNote(PerfumeDetailNote.TOP)
        } else {
            setSelectedNote(null)
        }
    }

    fun setPerfumeLike() {
        _isLiked.value = _perfumeDetailItem.value?.isLiked
        _likeCount.value = _perfumeDetailItem.value?.likeCount?.toInt()
    }

    fun setSelectedNote(note: PerfumeDetailNote?) {
        _selectedNote.value = note
    }

    fun setNoteContents() {
        when (_selectedNote.value) {
            PerfumeDetailNote.TOP -> {
                _perfumeDetailItem.value?.notes?.top?.let { top ->
                    _noteContents.value = formatNoteContents(top)
                }
            }
            PerfumeDetailNote.MIDDLE -> {
                _perfumeDetailItem.value?.notes?.middle?.let { middle ->
                    _noteContents.value = formatNoteContents(middle)
                }
            }
            PerfumeDetailNote.BASE -> {
                _perfumeDetailItem.value?.notes?.base?.let { base ->
                    _noteContents.value = formatNoteContents(base)
                }
            }
            else -> {
                _perfumeDetailItem.value?.notes?.default?.let { default ->
                    _noteContents.value = formatNoteContents(default)
                }
            }
        }
    }

    private fun formatNoteContents(note: List<Note>): String {
        return TextUtils.join(
            ", ",
            note.map { it.koreanName }
        )
    }

    fun likePerfume() {
        val perfumeId = _perfumeId.value ?: return
        viewModelScope.launch(Dispatchers.IO) {
            perfumeDetailRepository.likePerfume(perfumeId)
            fetchPerfumeDetail()
            setPerfumeLike()
        }
    }

    enum class PerfumeDetailNote {
        TOP,
        MIDDLE,
        BASE
    }
}