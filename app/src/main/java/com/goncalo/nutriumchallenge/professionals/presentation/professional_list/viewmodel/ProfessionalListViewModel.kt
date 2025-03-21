package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetLastFilterUsedUseCase
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetProfessionalListUseCase
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSortType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ProfessionalListViewModel internal constructor(
    private val getProfessionalListUseCase: GetProfessionalListUseCase,
    private val getLastFilterUsedUseCase: GetLastFilterUsedUseCase
) : ViewModel() {

    private val _sortOption: MutableStateFlow<ProfessionalSortType?> =
        MutableStateFlow(null)
    val sortOption = _sortOption.asStateFlow()

    init {
        //Start by getting the last filter used from DataStore
        getLastFilterSelected()
    }

    val professionalsList = sortOption.flatMapLatest { sortOrder ->
        val filter = sortOrder ?: ProfessionalSortType.BEST_MATCH
        getProfessionalListUseCase(filter)
    }.cachedIn(viewModelScope)

    fun changeSortOptionSelected(newSort: ProfessionalSortType) {
        _sortOption.value = newSort
    }

    private fun getLastFilterSelected() = viewModelScope.launch {
        _sortOption.value =
            getLastFilterUsedUseCase()?.let { ProfessionalSortType.getEnumByTypeName(it) }
                ?: ProfessionalSortType.BEST_MATCH
    }
}