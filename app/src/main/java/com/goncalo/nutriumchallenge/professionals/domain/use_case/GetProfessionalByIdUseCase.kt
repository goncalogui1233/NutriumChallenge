package com.goncalo.nutriumchallenge.professionals.domain.use_case

import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository

class GetProfessionalByIdUseCase(
    private val repository: ProfessionalRepository
) {

    suspend operator fun invoke(uniqueId: String) = repository.getProfessionalDetail(
        uniqueId
    )

}