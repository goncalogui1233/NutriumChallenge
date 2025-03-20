package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest

class ProfessionalListViewModel internal constructor(
    repository: ProfessionalRepository
) : ViewModel(){

    private val _sortOption: MutableStateFlow<ProfessionalSort> =
        MutableStateFlow(ProfessionalSort.MOST_POPULAR)
    val sortOption = _sortOption.asStateFlow()


    val professionalsList = sortOption.flatMapLatest { sortOrder ->
        repository.getProfessionalList(sortOrder)
    }.cachedIn(viewModelScope)

    fun changeSortOptionSelected(newSort: ProfessionalSort) {
        _sortOption.value = newSort
    }
}


sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Success<T>(val data: T?): UiState<T>()
    data class Error(val message: String?): UiState<Nothing>()
}

enum class ProfessionalSort(val typeName: String) {
    BEST_MATCH("best_match"),
    RATING("rating"),
    MOST_POPULAR("most_popular")
}