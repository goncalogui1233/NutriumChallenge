package com.goncalo.nutriumchallenge.professionals.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.goncalo.nutriumchallenge.professionals.data.database.ProfessionalDao
import com.goncalo.nutriumchallenge.professionals.data.datastore.ProfessionalDataStore
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional

@OptIn(ExperimentalPagingApi::class)
class ProfessionalRemoteMediator(
    private val api: ProfessionalApi,
    private val db: ProfessionalDao,
    private val dataStore: ProfessionalDataStore,
    private val sortType: String
) : RemoteMediator<Int, Professional>() {

    private var currentPage = 0
    private var totalPages = 0

    override suspend fun initialize(): InitializeAction {
        return if (db.getAllProfessionalItems().isEmpty()) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            val listMarkers = dataStore.getProfessionalListPageMarker()

            //Values for currentPage and totalPages have been lost, we need to reset
            if (listMarkers.first.isNullOrEmpty() || listMarkers.second.isNullOrEmpty()) {
                InitializeAction.LAUNCH_INITIAL_REFRESH
            } else {
                totalPages = (listMarkers.first ?: return InitializeAction.LAUNCH_INITIAL_REFRESH).toInt()
                currentPage = (listMarkers.second ?: return InitializeAction.LAUNCH_INITIAL_REFRESH).toInt()
                InitializeAction.SKIP_INITIAL_REFRESH
            }
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Professional>
    ): MediatorResult {
        return try {
            val loadKey = when(loadType) {
                LoadType.REFRESH -> {
                    currentPage = 0
                    0
                }
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    if (currentPage >= totalPages) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    ++currentPage
                }
            }

            val response = api.getProfessionalsList(sortType, loadKey.toString())

            response.body()?.let {
                if(loadType == LoadType.REFRESH) {
                    db.clearProfessionalTable()
                }

                db.insertALlProfessional(it.professionals)
                totalPages = it.count
                dataStore.saveProfessionalListPageMarker(currentPage.toString(), totalPages.toString())

                MediatorResult.Success(
                    endOfPaginationReached = it.count <= currentPage
                )
            } ?: MediatorResult.Success(
                endOfPaginationReached = true
            )
        } catch(e: Exception) {
            MediatorResult.Error(e)
        }
    }
}