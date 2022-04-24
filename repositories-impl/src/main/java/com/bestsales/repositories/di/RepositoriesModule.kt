package com.bestsales.repositories.di

import com.bestsales.repositories.ISearchRepository
import com.bestsales.repositories.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun provideSearRepository(repImpl: SearchRepository) : ISearchRepository
}