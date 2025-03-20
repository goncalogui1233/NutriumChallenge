package com.goncalo.nutriumchallenge.professionals.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalPagingSource
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalSort
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject

class ProfessionalRepositoryImpl(
    private val api: ProfessionalApi,
) : ProfessionalRepository {
    override fun getProfessionalList(sort: ProfessionalSort): Flow<PagingData<Professional>> = Pager(
        config = PagingConfig(4),
        pagingSourceFactory = { ProfessionalPagingSource(api, sort.typeName) }
    ).flow

    override suspend fun getProfessionalDetail(id: String): Status<Professional?> {
        return try {
            val response = api.getProfessionalById(id)

            if (response.isSuccessful) {
                Status(isSuccess = true, content = response.body()!!)
            } else {
                Status(isSuccess = false, errorMessage = convertErrorBody(response.errorBody()?.string()))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Status(isSuccess = false, errorMessage = "")
        }

    }
}

fun convertErrorBody(errorString: String?): String {

    return errorString?.let {
        val json = Json.parseToJsonElement(errorString)
        json.jsonObject["error"].toString()
    } ?: ""
}

data class Status<T>(
    val isSuccess: Boolean,
    val content: T? = null,
    val errorMessage: String? = null
)

