package com.goncalo.nutriumchallenge.professionals.data.repository

import androidx.paging.PagingData
import com.goncalo.nutriumchallenge.common.Status
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSort
import kotlinx.coroutines.flow.Flow

interface ProfessionalRepository {

    fun getProfessionalList(sort: ProfessionalSort): Flow<PagingData<Professional>>

    suspend fun getProfessionalDetail(uniqueId: String) : Status<Professional?>

}