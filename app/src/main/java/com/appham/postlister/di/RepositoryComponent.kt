package com.appham.postlister.di

import com.appham.postlister.viewmodel.DetailsViewModel
import com.appham.postlister.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component
@Singleton
interface RepositoryComponent {
    fun inject(viewModel: MainViewModel)
    fun inject(viewModel: DetailsViewModel)
}