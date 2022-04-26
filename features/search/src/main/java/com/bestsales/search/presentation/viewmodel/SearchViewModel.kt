package com.bestsales.search.presentation.viewmodel

import android.util.AndroidRuntimeException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import com.bestsales.repositories.ISearchRepository
import com.bestsales.repositories.domain.ProductDto
import com.bestsales.uikit.cards.productCard.ProductItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

class SearchViewModel @Inject constructor(var repository: ISearchRepository) :
    ViewModel() {

    companion object {
        const val SEARCH_DELAY = 1000L
        const val SEARCH_DEBOUNCE = 1000L
        const val MIN_QUERY_LENGTH = 3
    }

    private val listData =
        MutableStateFlow<SearchUiState>(SearchUiState.Initial)
    val uiState: StateFlow<SearchUiState> = listData

    fun startSearchWithDelay(query: String) {
        viewModelScope.launch {
            flowOf(query)
                .dropWhile {
                    it.length < MIN_QUERY_LENGTH
                }
                .debounce(SEARCH_DEBOUNCE)
                .collect {
                    startSearch(query = query)
                        .onStart {
                            listData.value = SearchUiState.Loading
                        }
                        .debounce(SEARCH_DELAY)
                        .map { mapResult(it) }
                        .catch { ex ->
                            listData.value = SearchUiState.Error(ErrorState.ERROR_LOADING)
                        }
                        .collect {
                            listData.value = SearchUiState.Success(flowOf(it))
                        }
                }
            if (query.isEmpty()) {
                listData.value = SearchUiState.Initial
            }
        }
    }

    fun fetchDataPaging() {
        viewModelScope.launch {
            startSearch()
                .catch {
                    listData.value = SearchUiState.Error(ErrorState.ERROR_LOADING)
                }
                .map { mapResult(it) }
                .collect {
                    listData.value = SearchUiState.Success(flowOf(it))
                }
        }
    }

    private suspend fun startSearch(query: String? = null): Flow<PagingData<ProductDto>> {
        return if (query.isNullOrEmpty()) {
            repository.startSearchByQuery()
        } else {
            repository.startSearchByQueryPaging(query = query)
        }
    }

    private fun mapResult(it: PagingData<ProductDto>): PagingData<ProductItem> {
        return it.map { item ->
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.CEILING
            ProductItem(
                title = item.name,
                imgLink = item.imgLink,
                price = df.format(item.price.salesPrice),
                basePrice = if (item.price.basePrice > 0) {
                    df.format(item.price.basePrice)
                } else {
                    ""
                },
                isNextDayDelivery = item.isNextDayDelivery
            )
        }
    }
}


