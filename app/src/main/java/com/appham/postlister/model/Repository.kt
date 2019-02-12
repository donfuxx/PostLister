package com.appham.postlister.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import javax.inject.Inject

class Repository @Inject constructor() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val executor by lazy {
        Executors.newSingleThreadExecutor()
    }


    fun posts(isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<List<Post>>) {

        isBusy.postValue(true)

        val disposable =
            ApiService.postsApi.getPosts()
            .subscribeOn(Schedulers.io())
            .subscribeBy(

                onNext = {
                    Log.d(javaClass.simpleName, "onNext: $it")
                    updatePosts(it, isSuccess)
                },

                onError = {
                    Log.e(javaClass.simpleName, "onError: $it")
                    isSuccess.postValue(null)
                    isBusy.postValue(false)
                },

                onComplete = {
                    Log.d(javaClass.simpleName, "onComplete!")
                    isBusy.postValue(false)
                }

            )

        compositeDisposable.add(disposable)

    }

    private fun updatePosts(posts: List<Post>, isSuccess: MutableLiveData<List<Post>>) {
        executor.execute {
            DbService.postsDb.postsDao().insert(posts)
            isSuccess.postValue(posts)
        }
    }

    fun dispose() {
        compositeDisposable.clear()
    }

}