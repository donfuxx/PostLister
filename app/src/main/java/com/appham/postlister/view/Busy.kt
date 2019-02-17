package com.appham.postlister.view

import androidx.lifecycle.LiveData

interface Busy {
    fun getIsBusy(): LiveData<Boolean>
}