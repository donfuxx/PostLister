package com.appham.postlister.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.Post
import com.appham.postlister.model.Repository
import javax.inject.Inject

class MainViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    private val isBusy = MutableLiveData<Boolean>()

    private val isSuccess = MutableLiveData<List<Post>>()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    fun getIsBusy(): LiveData<Boolean> {
        return isBusy
    }

    fun getIsSuccess(): LiveData<List<Post>> {
        return isSuccess
    }

    fun getPosts() {
        repository.posts(isBusy, isSuccess)
    }
}