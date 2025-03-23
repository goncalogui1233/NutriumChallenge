package com.goncalo.nutriumchallenge.professionals.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional

class ProfessionalPagingSource(
    private val items: List<Professional>
) : PagingSource<Int, Professional>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Professional> {
        val start = params.key ?: 0
        val end = minOf(start + params.loadSize, items.size)

        val pageItems = items.subList(start, end)

        return LoadResult.Page(
            data = pageItems,
            prevKey = if (start == 0) null else start - params.loadSize,
            nextKey = if (end == items.size) null else end
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Professional>): Int? = null
}