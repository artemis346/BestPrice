package com.bestsales.repositories

import androidx.paging.*
import com.bestsales.network.api.search.SearchApi
import com.bestsales.repositories.domain.ProductDto
import com.bestsales.repositories.mappers.mapToDomain
import java.lang.Exception

class SearchSource constructor(private val api: SearchApi, private var query: String) :
    PagingSource<Int, ProductDto>() {

    companion object {
        const val OFFSET = 5
        const val FIRST_PAGE = 1
    }

    override fun getRefreshKey(state: PagingState<Int, ProductDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(OFFSET)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductDto> {
        val nextPage = params.key ?: FIRST_PAGE
        return try {
            val response = api.searchByQuery(query, nextPage)
            val list = response.mapToDomain()
            LoadResult.Page(
                data = list,
                prevKey = null,
                nextKey = if (response.currentPage == response.pageCount) {
                    null
                } else {
                    response.currentPage + 1
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}