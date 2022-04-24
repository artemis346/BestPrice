package com.bestsales.search.presentation.viewmodel

import androidx.annotation.StringRes
import androidx.paging.PagingData
import com.bestsales.search.R
import com.bestsales.uikit.cards.productCard.ProductItem
import kotlinx.coroutines.flow.Flow

sealed class SearchUiState {
    data class Success(val products: Flow<PagingData<ProductItem>>) : SearchUiState()
    data class Error(val error: ErrorState) : SearchUiState()
    object Initial : SearchUiState()
    object Loading : SearchUiState()
}

enum class ErrorState(@StringRes val message: Int) {
    ERROR_LOADING(R.string.error_content_try_again),
    EMPTY_CONTENT(R.string.error_content_empty_content)
}