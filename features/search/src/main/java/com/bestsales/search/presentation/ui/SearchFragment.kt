package com.bestsales.search.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bestsales.search.R
import com.bestsales.search.di.DaggerSearchComponent
import com.bestsales.search.di.SearchModuleDependencies
import com.bestsales.uikit.cards.productCard.ProductItem
import com.bestsales.search.presentation.viewmodel.SearchUiState
import com.bestsales.search.presentation.viewmodel.SearchViewModel
import com.bestsales.uikit.cards.productCard.ProductCard
import com.bestsales.uikit.emptyList.EmptyState
import com.bestsales.uikit.error.ErrorView
import com.bestsales.uikit.shimmer.AnimatedShimmer
import com.bestsales.uikit.textFields.SearchTextField
import kotlinx.coroutines.flow.Flow


@AndroidEntryPoint
class SearchFragment : Fragment() {

    companion object {
        const val SHIMMER_SIZE = 5
    }

    @Inject
    lateinit var searchVM: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DaggerSearchComponent.builder()
            .appDependencies(
                EntryPointAccessors.fromFragment(
                    this,
                    SearchModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                SearchResultScreen(searchVM)
            }
        }
    }

    @Composable
    fun SearchResultScreen(vm: SearchViewModel) {
        Scaffold(
            backgroundColor = colorResource(id = R.color.white_grey),
            topBar = { ScreenHeader(vm = vm) },
            content = { ScreenContent(vm = vm) }

        )
    }

    @Composable
    fun ScreenContent(vm: SearchViewModel) {
        when (val state = vm.uiState.collectAsState().value) {
            is SearchUiState.Initial -> {
                EmptyState(getString(R.string.empty_list), R.drawable.ic_search_large)
            }
            is SearchUiState.Success -> {
                ProductsList(vm, state.products)
            }
            is SearchUiState.Loading -> {
                Shimmer(SHIMMER_SIZE)
            }
            is SearchUiState.Error -> {
                Error(vm, getString(state.error.message))
            }
        }
    }

    @Composable
    fun Error(vm: SearchViewModel, message: String) {
        ErrorView(message) {
            vm.fetchDataPaging()
        }
    }

    @Composable
    fun ScreenHeader(vm: SearchViewModel) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            color = MaterialTheme.colors.primary,
            elevation = 8.dp,
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                SearchTextField(
                    placeholder = R.string.search_placeholder,
                    onValueChange = {
                        vm.startSearchWithDelay(it)
                    })
            }
        }
    }

    @Composable
    fun Shimmer(shimmerSize: Int) {
        Column {
            repeat(shimmerSize) {
                AnimatedShimmer()
            }
        }
    }

    @Composable
    fun ProductsList(vm: SearchViewModel, products: Flow<PagingData<ProductItem>>) {
        val items = products.collectAsLazyPagingItems()
        when {
            items.loadState.append is LoadState.Error || items.loadState.refresh is LoadState.Error -> {
                Error(vm, getString(R.string.error_content_try_again))
            }
            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(items.itemCount) { index ->
                        items[index]?.let { product ->
                            ListItem(product)
                        }

                    }
                }
            }
        }

    }

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun ListItem(products: ProductItem) {
        ProductCard(
            item = products,
            onClick = {
                Toast.makeText(
                    context,
                    getString(R.string.section_under_development),
                    Toast.LENGTH_SHORT
                ).show()
            }
        )
    }


}