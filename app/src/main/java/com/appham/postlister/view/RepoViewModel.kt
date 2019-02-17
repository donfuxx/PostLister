package com.appham.postlister.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appham.postlister.model.Repository
import javax.inject.Inject

abstract class RepoViewModel : ViewModel(), Busy {

    @Inject
    lateinit var repository: Repository

    protected val isBusy: MutableLiveData<Boolean> = MutableLiveData()

    override fun getIsBusy(): LiveData<Boolean> {
        return isBusy
    }
}