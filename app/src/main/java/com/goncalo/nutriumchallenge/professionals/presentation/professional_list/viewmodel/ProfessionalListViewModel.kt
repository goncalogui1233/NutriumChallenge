package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSort
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