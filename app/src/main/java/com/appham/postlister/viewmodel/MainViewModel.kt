package com.appham.postlister.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.data.Post

class MainViewModel : RepoViewModel(), PostsLoadedCallback {
    private val loadedPosts = MutableLiveData<List<Post>>()

    private val nextScreen = MutableLiveData<Post>()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    fun getLoadedPosts(): LiveData<List<Post>> {
        return loadedPosts
    }

    fun getNextScreen(): LiveData<Post> {
        return nextScreen
    }

    fun getPosts() {
        repository.posts(this, this)
    }

    override fun setPosts(posts: List<Post>) {
        loadedPosts.postValue(posts)
    }

    fun navigate(post: Post) {
        nextScreen.postValue(post)
    }

    override fun onCleared() {
        super.onCleared()
        repository.dispose()
    }
}