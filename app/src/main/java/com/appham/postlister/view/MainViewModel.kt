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

    private val isBusy: MutableLiveData<Boolean> = MutableLiveData()

    private val isSuccess = MutableLiveData<List<Post>>()

    private val nextScreen = MutableLiveData<Post>()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    fun getIsBusy(): LiveData<Boolean> {
        return isBusy
    }

    fun getIsSuccess(): LiveData<List<Post>> {
        return isSuccess
    }

    fun getNextScreen(): LiveData<Post> {
        return nextScreen
    }

    fun getPosts() {
        repository.posts(isBusy, isSuccess)
    }

    fun navigate(userId: Post) {
        nextScreen.postValue(userId)
    }
}