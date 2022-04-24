package com.bestsales.repositories

import androidx.paging.PagingData
import com.bestsales.repositories.domain.ProductDto
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    suspend fun startSearchByQuery(): Flow<PagingData<ProductDto>>
    suspend fun startSearchByQueryPaging(query: String): Flow<PagingData<ProductDto>>
}