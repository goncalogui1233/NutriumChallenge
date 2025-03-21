package com.goncalo.nutriumchallenge.professionals.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.goncalo.nutriumchallenge.common.Status
import com.goncalo.nutriumchallenge.common.isTimestampOutOfRange
import com.goncalo.nutriumchallenge.professionals.data.database.ProfessionalDao
import com.goncalo.nutriumchallenge.professionals.data.datastore.ProfessionalDataStore
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.data.paging.ProfessionalRemoteMediator
import com.goncalo.nutriumchallenge.professionals.data.repository.ProfessionalRepository
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSort
import kotlinx.coroutines.flow.Flow

class ProfessionalRepositoryImpl(
    private val api: ProfessionalApi,
    private val db: ProfessionalDao,
    private val dataStore: ProfessionalDataStore
) : ProfessionalRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getProfessionalList(sort: ProfessionalSort): Flow<PagingData<Professional>> = Pager(
        config = PagingConfig(4),
        remoteMediator = ProfessionalRemoteMediator(api, db, dataStore, sort.typeName)
    ){
        db.getAllProfessionalsPagingSource()
    }.flow

    override suspend fun getProfessionalDetail(uniqueId: String): Status<Professional?> {
        val professionalDbData = db.getProfessionalByUniqueId(uniqueId)
        return professionalDbData?.let { professionalDetailDb ->
            val updateDetails = professionalDetailDb.detailTimestamp.isTimestampOutOfRange(TIME_TO_UPDATE_DETAIL_MIN)

            val professionalDetailToView = if (updateDetails) {
                try {
                    //Fetch detail from api. If response fails, returns information already saved in the DB
                    val response = api.getProfessionalById(professionalDetailDb.id.toString())
                    if (response.isSuccessful && response.body() != null) {
                        response.body()?.let { professionalDetailRemote ->
                            db.updateProfessional(
                                professionalDetailRemote.copy(
                                    uniqueId = professionalDetailDb.uniqueId,
                                    detailTimestamp = System.currentTimeMillis()
                                )
                            )
                            professionalDetailRemote
                        }
                    } else { //Request unsuccessful, return data from DB
                        professionalDetailDb
                    }
                } catch (e: Exception) { //Exception thrown, return data from DB
                    professionalDetailDb
                }
            } else { //Data in DB still updated, return data from DB
                professionalDetailDb
            }

            Status(isSuccess = true, content = professionalDetailToView)
        } ?: kotlin.run {
            Status(isSuccess = false)
        }
    }

    override suspend fun getLastFilterUsed() = dataStore.getLastFilterSelected()

    companion object {
        private const val TIME_TO_UPDATE_DETAIL_MIN = 5
    }
}
