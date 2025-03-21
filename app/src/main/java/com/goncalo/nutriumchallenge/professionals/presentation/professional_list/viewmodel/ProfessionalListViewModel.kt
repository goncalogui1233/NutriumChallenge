package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSort
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ProfessionalListViewModel internal constructor(
    private val repository: ProfessionalRepository
) : ViewModel(){

    private val _sortOption: MutableStateFlow<ProfessionalSort?> =
        MutableStateFlow(null)
    val sortOption = _sortOption.asStateFlow()

    init {
        getLastFilterSelected()
    }

    val professionalsList = sortOption.flatMapLatest { sortOrder ->
        val filter = sortOrder ?: ProfessionalSort.BEST_MATCH
        repository.getProfessionalList(filter)
    }.cachedIn(viewModelScope)

    fun changeSortOptionSelected(newSort: ProfessionalSort) {
        _sortOption.value = newSort
    }

    private fun getLastFilterSelected() = viewModelScope.launch {
        _sortOption.value = repository.getLastFilterUsed()?.let { ProfessionalSort.getEnumByTypeName(it) }
            ?: ProfessionalSort.BEST_MATCH
    }
}