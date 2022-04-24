package com.bestsales.network.api.search

import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApi {

    @GET("mobile-assignment/search")
    suspend fun searchByQuery(
        @Query("query")
        query: String,

        @Query("page")
        page: Int,
    ): SearchResponse
}