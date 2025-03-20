package com.goncalo.nutriumchallenge.professionals.domain.use_case

import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository

class GetProfessionalByIdUseCase(
    private val repository: ProfessionalRepository
) {

    suspend operator fun invoke(id: String) = repository.getProfessionalDetail(id)

}