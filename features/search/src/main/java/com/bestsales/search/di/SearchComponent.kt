package com.bestsales.search.di

import com.bestsales.search.presentation.ui.SearchFragment
import dagger.Component

@Component(dependencies = [SearchModuleDependencies::class])
interface SearchComponent {

    fun inject(fmt: SearchFragment)

    @Component.Builder
    interface Builder {
        fun appDependencies(deps: SearchModuleDependencies): Builder
        fun build(): SearchComponent
    }
}