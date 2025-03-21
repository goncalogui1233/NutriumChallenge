package com.goncalo.nutriumchallenge.professionals.domain.use_case

import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSortType

class GetProfessionalListUseCase(
    private val repository: ProfessionalRepository
) {

    suspend operator fun invoke(sortType: ProfessionalSortType) =
        repository.getProfessionalList(sortType)

}