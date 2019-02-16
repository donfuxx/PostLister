package com.appham.postlister.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appham.postlister.di.DaggerRepositoryComponent
import com.appham.postlister.model.Repository
import com.appham.postlister.model.User
import javax.inject.Inject

class DetailsViewModel : ViewModel(), Busy {

    @Inject
    lateinit var repository: Repository

    var userId: Int = 0
    lateinit var postTitle: String
    lateinit var postBody: String

    private val isSuccess: MutableLiveData<User> = MutableLiveData()

    init {
        DaggerRepositoryComponent.builder().build().inject(this)
    }

    fun getIsSuccess(): LiveData<User>{
        return isSuccess
    }

    fun loadPostDetails() {
        repository.user(userId, isBusy, isSuccess)
    }


}
