package com.bestsales.search.di

import com.bestsales.repositories.ISearchRepository
import com.bestsales.search.presentation.viewmodel.SearchViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
internal class SearchModule {

    @Provides
    fun provideViewModel(repository: ISearchRepository) : SearchViewModel {
        return SearchViewModel(repository)
    }
}