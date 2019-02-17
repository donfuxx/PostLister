package com.appham.postlister.viewmodel

import androidx.lifecycle.LiveData

interface Busy {
    fun getIsBusy(): LiveData<Boolean>
}