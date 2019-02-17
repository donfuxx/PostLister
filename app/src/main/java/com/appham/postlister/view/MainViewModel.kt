package com.appham.postlister.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.Post

class MainViewModel : RepoViewModel() {

    private val isSuccess = MutableLiveData<List<Post>>()

    private val nextScreen = MutableLiveData<Post>()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
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

    fun navigate(post: Post) {
        nextScreen.postValue(post)
    }

    override fun onCleared() {
        super.onCleared()
        repository.dispose()
    }
}