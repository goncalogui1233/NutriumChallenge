package com.goncalo.nutriumchallenge.professionals.presentation.common.helpers

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object ProfessionalList: Screens()

    @Serializable
    data class ProfessionalDetails(val professionalUniqueId: Int): Screens()

}