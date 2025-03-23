package com.goncalo.nutriumchallenge.professionals.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goncalo.nutriumchallenge.common.Status
import com.goncalo.nutriumchallenge.professionals.data.paging.ProfessionalPagingSource
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSortType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

class FakeProfessionalRepository : ProfessionalRepository {

    val listProfessional = listOf(Professional(
        2,
        listOf("Weight Loss"),
        2,
        listOf("Portuguese, Spanish"),
        "John Brown",
        5,
        "",
        350
    ), Professional(
        5,
        listOf("Weight Loss"),
        2,
        listOf("Portuguese"),
        "John Rato",
        5,
        "",
        350
    ))

    override fun getProfessionalList(sort: ProfessionalSortType): Flow<PagingData<Professional>> {
        return Pager(
            config = PagingConfig(1),
            pagingSourceFactory = {ProfessionalPagingSource(listProfessional) }
        ).flow
    }

    override suspend fun getProfessionalDetail(uniqueId: String): Status<Professional?> {

        val professional = listProfessional.firstOrNull { it.uniqueId.toString() == uniqueId }

        return professional?.let {
            Status(isSuccess = true, content = it)
        } ?: kotlin.run {
            Status(isSuccess = false, errorMessage = "No Professional")
        }
    }

    override suspend fun getLastFilterUsed(): String? {
        return ProfessionalSortType.BEST_MATCH.typeName
    }
}