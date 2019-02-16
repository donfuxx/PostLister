package com.appham.postlister.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.Repository
import com.appham.postlister.model.User
import javax.inject.Inject

class DetailsViewModel : ViewModel() {

    @Inject
    lateinit var repository: Repository

    var userId: Int = 0
    lateinit var postTitle: String
    lateinit var postBody: String

    private val isSuccessUser: MutableLiveData<User> = MutableLiveData()
    private val isSuccessComments: MutableLiveData<Int> = MutableLiveData()
    private val isBusy: MutableLiveData<Boolean> = MutableLiveData()

    private val userObserver: Observer<User> = Observer {
        repository.commentsCount(it.email, isBusy, isSuccessComments)
    }

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    fun getIsBusy(): LiveData<Boolean> {
        return isBusy
    }

    fun getIsSuccessUser(): LiveData<User>{
        return isSuccessUser
    }

    fun getIsSuccessComments(): LiveData<Int> {
        return isSuccessComments
    }

    fun loadPostDetails() {
        repository.user(userId, isBusy, isSuccessUser)

        isSuccessUser.observeForever(userObserver)
    }

    override fun onCleared() {
        super.onCleared()
        isSuccessUser.removeObserver(userObserver)
        repository.dispose()
    }

}
