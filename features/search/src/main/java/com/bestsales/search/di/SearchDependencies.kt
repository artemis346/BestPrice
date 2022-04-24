package com.bestsales.search.di

import com.bestsales.search.presentation.viewmodel.SearchViewModel
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@EntryPoint
@InstallIn(FragmentComponent::class)
interface SearchModuleDependencies {
    fun searchViewModel(): SearchViewModel
}
