package com.goncalo.nutriumchallenge.professionals.presentation

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object ProfessionalList: Screens()

    @Serializable
    data class ProfessionalDetails(val professionalId: Int): Screens()

}