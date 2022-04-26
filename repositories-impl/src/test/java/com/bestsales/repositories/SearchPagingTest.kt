package com.bestsales.repositories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.paging.PagingSource

import com.bestsales.network.api.search.SearchApi
import com.bestsales.repositories.mappers.mapToDomain
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class SearchPagingTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var api: SearchApi

    lateinit var searchPagingSource: SearchSource

    @Before
    fun setup() {
        api = mock(SearchApi::class.java)
        MockitoAnnotations.initMocks(this)
        searchPagingSource = SearchSource(api, query = "test")
    }

    @Test
    fun `Paging positive case`() = runTest() {
        given(api.searchByQuery("test", 1)).willReturn(responseValid)

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseValid.mapToDomain(),
            prevKey = null,
            nextKey = 2
        )

        assertEquals(expectedResult, searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1, loadSize = 24, placeholdersEnabled = false
            )
        ))
    }

    @Test
    fun `Paging empty case`() = runTest() {
        given( api.searchByQuery("test",1) ).willReturn(responseEmpty)

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseEmpty.mapToDomain(),
            prevKey = null,
            nextKey = null
        )

        assertEquals(expectedResult, searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1, loadSize = 24, placeholdersEnabled = false
            )
        ))
    }

    @Test
    fun `Paging null case`() = runTest() {
        given( api.searchByQuery("test",1) ).willReturn(responseNull)

        val expectedResult = PagingSource.LoadResult.Page(
            data = responseEmpty.mapToDomain(),
            prevKey = null,
            nextKey = null
        )

        assertEquals(expectedResult, searchPagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 1, loadSize = 24, placeholdersEnabled = false
            )
        ))
    }
}