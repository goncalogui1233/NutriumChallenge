package com.goncalo.nutriumchallenge.common

data class Status<T>(
    val isSuccess: Boolean,
    val content: T? = null,
    val errorMessage: String? = null
)
