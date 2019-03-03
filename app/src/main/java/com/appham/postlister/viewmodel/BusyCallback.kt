package com.appham.postlister.viewmodel

import androidx.lifecycle.LiveData

interface BusyCallback {

    fun getIsBusy(): LiveData<Boolean>

    fun setBusy(isBusyValue: Boolean)
}
