package com.appham.postlister.view

import androidx.lifecycle.MutableLiveData

interface Busy {

    val isBusy: MutableLiveData<Boolean>
        get() = MutableLiveData<Boolean>()
}