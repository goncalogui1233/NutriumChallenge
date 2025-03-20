package com.goncalo.nutriumchallenge.professionals.domain.model

data class ProfessionalListOutput(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val professionals: List<Professional>
)