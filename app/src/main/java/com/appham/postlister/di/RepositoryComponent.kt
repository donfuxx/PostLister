package com.appham.postlister.di

import com.appham.postlister.view.DetailsViewModel
import com.appham.postlister.view.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface RepositoryComponent {
    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: DetailsViewModel)
}