package com.goncalo.nutriumchallenge.professionals.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.goncalo.nutriumchallenge.professionals.data.network.ProfessionalApi
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional


/**
 * Paging Source to load list of items. To use only for the list in case we want to remove DB from
 * application.
 */
class ProfessionalPagingSource(
    private val api: ProfessionalApi,
    private val sortType: String,
) : PagingSource<Int, Professional>() {
    override fun getRefreshKey(state: PagingState<Int, Professional>) = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Professional> {
        try {
            val nextPage = params.key ?: 0
            val response = api.getProfessionalsList(sortType, nextPage.toString())

            response.body()?.professionals?.let { professionalsList ->
                return LoadResult.Page(
                    data = professionalsList,
                    prevKey = null,
                    nextKey = if (professionalsList.isEmpty()) null else (nextPage + 1)
                )
            } ?: kotlin.run {
                return LoadResult.Error(NullPointerException(response.message()))
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}