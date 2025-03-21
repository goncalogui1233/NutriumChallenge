package com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.domain.use_case.GetProfessionalByIdUseCase
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfessionalDetailViewModel(
    private val getProfessionalByIdUseCase: GetProfessionalByIdUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState<Professional>?> = MutableStateFlow(null)
    val uiState = _uiState.asStateFlow()


    fun getProfessionalDetail(uniqueId: Int) = viewModelScope.launch(Dispatchers.IO) {
        _uiState.emit(UiState.Loading)
        val result = getProfessionalByIdUseCase(uniqueId.toString())
        if(result.isSuccess) {
            _uiState.emit(UiState.Success(result.content))
        } else {
            _uiState.emit(UiState.Error(result.errorMessage))
        }
    }
}