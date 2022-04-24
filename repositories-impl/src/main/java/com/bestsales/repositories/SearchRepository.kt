package com.bestsales.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bestsales.network.api.search.SearchApi
import com.bestsales.network.api.search.SearchResponse
import com.bestsales.repositories.domain.ProductDto
import com.bestsales.repositories.mappers.mapToDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject


class SearchRepository @Inject constructor(
    private val api: SearchApi
) : ISearchRepository {

    companion object {
        const val PAGE_SIZE = 24
    }

    private var lastQuery = ""

    override suspend fun startSearchByQuery(): Flow<PagingData<ProductDto>> {
        return startSearch()
    }

    override suspend fun startSearchByQueryPaging(query: String): Flow<PagingData<ProductDto>> {
        lastQuery = query
        return startSearch()
    }

    private fun startSearch(): Flow<PagingData<ProductDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = {
                SearchSource(
                    api = api, query = lastQuery
                )
            }).flow
    }
}