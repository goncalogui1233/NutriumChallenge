package com.goncalo.nutriumchallenge.professionals.presentation.common.helpers

sealed class UiState<out T> {
    data object Loading: UiState<Nothing>()
    data class Success<T>(val data: T?): UiState<T>()
    data class Error(val message: String?): UiState<Nothing>()
}