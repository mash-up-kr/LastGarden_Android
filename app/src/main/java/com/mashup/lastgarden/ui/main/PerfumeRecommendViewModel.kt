package com.mashup.lastgarden.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.mashup.lastgarden.data.repository.PerfumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PerfumeRecommendViewModel @Inject constructor(
    perfumeRepository: PerfumeRepository
) : ViewModel() {

    companion object {
        private const val PAGE_SIZE = 10
    }

    val perfumeRecommendItems: Flow<PagingData<PerfumeRecommendItem>> = perfumeRepository
        .getRecommendPerfumes(PAGE_SIZE)
        .map { pagingData -> pagingData.map { perfume -> perfume.toRecommendPerfumeItem() } }
        .cachedIn(viewModelScope)
}