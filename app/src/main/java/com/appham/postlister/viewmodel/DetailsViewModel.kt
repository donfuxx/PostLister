package com.appham.postlister.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.data.User

class DetailsViewModel : RepoViewModel(), UserLoadedCallback, CommentsLoadedCallback {
    var postId: Int = 0

    var userId: Int = 0

    lateinit var postTitle: String
    lateinit var postBody: String
    private val isSuccessUser: MutableLiveData<User> = MutableLiveData()
    private val isSuccessComments: MutableLiveData<Int> = MutableLiveData()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }
    override fun setUser(user: User) {
        isSuccessUser.postValue(user)
    }

    override fun setComments(commentsCount: Int) {
        isSuccessComments.postValue(commentsCount)
    }

    fun getIsSuccessUser(): LiveData<User>{
        return isSuccessUser
    }

    fun getIsSuccessComments(): LiveData<Int> {
        return isSuccessComments
    }

    fun loadPostDetails() {
        repository.user(userId, this, this)
        repository.commentsCount(postId, this, this)
    }

    override fun onCleared() {
        super.onCleared()
        repository.dispose()
    }

}
