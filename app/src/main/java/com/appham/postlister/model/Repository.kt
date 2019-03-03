package com.appham.postlister.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.model.api.ApiService
import com.appham.postlister.model.data.Comment
import com.appham.postlister.model.data.Post
import com.appham.postlister.model.data.User
import com.appham.postlister.model.db.DbService
import com.appham.postlister.viewmodel.BusyCallback
import com.appham.postlister.viewmodel.PostsLoadedCallback
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Repository @Inject constructor() {

    private val compositeDisposable by lazy {
        CompositeDisposable()
    }

    private val executor by lazy {
        Executors.newSingleThreadExecutor()
    }

    fun posts(busyCallback: BusyCallback, postsLoadedCallback: PostsLoadedCallback) {

        busyCallback.setBusy(true)

        val disposable =
            ApiService.postsApi.getPosts()
            .subscribeOn(Schedulers.io())
            .subscribeBy(

                onSuccess = {
                    Log.d(javaClass.simpleName, "onSuccess: $it")
                    updatePosts(it, busyCallback, postsLoadedCallback)
                },

                onError = {
                    Log.e(javaClass.simpleName, "onError: $it")
                    postsLoadedCallback.setPosts(listOf())
                    busyCallback.setBusy(false)
                }

            )

        compositeDisposable.add(disposable)

    }

    fun users(busyCallback: BusyCallback, isSuccess: MutableLiveData<List<User>>) {
        busyCallback.setBusy(true)

        val disposable =
            ApiService.usersApi.getUsers()
                .subscribeOn(Schedulers.io())
                .subscribeBy(

                    onSuccess = {
                        Log.d(javaClass.simpleName, "onSuccess: $it")
                        updateUsers(it, busyCallback, isSuccess)
                    },

                    onError = {
                        Log.e(javaClass.simpleName, "onError: $it")
                        isSuccess.postValue(null)
                        busyCallback.setBusy(false)
                    }

                )

        compositeDisposable.add(disposable)
    }

    fun user(id: Int, busyCallback: BusyCallback, isSuccess: MutableLiveData<User>) {
        val users: MutableLiveData<List<User>> = MutableLiveData()
        users(busyCallback, users)

        users.observeForever { userList ->
            userList?.takeIf { it.isNotEmpty() }.let {
                executor.execute {
                    isSuccess.postValue(DbService.postsDb.usersDao().getById(id))
                }
            }
        }
    }

    fun comments(isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<List<Comment>>) {
        isBusy.postValue(true)

        val disposable =
            ApiService.commentsApi.getComments()
                .subscribeOn(Schedulers.io())
                .subscribeBy(

                    onSuccess = {
                        Log.d(javaClass.simpleName, "onSuccess: $it")
                        updateComments(it, isSuccess)
                        isBusy.postValue(false)
                    },

                    onError = {
                        Log.e(javaClass.simpleName, "onError: $it")
                        isSuccess.postValue(null)
                        isBusy.postValue(false)
                    }

                )

        compositeDisposable.add(disposable)
    }

    fun commentsCount(postId: Int, isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<Int>) {
        val comments: MutableLiveData<List<Comment>> = MutableLiveData()
        comments(isBusy, comments)

        comments.observeForever { commentList ->
            commentList?.takeIf { it.isNotEmpty() }.let {
                executor.execute {
                    isSuccess.postValue(DbService.postsDb.commentsDao().getCount(postId))
                }
            }
        }
    }

    private fun updatePosts(posts: List<Post>, busyCallback: BusyCallback, postsLoadedCallback: PostsLoadedCallback) {
        executor.execute {
            DbService.postsDb.postsDao().insert(posts)
            postsLoadedCallback.setPosts(posts)
            busyCallback.setBusy(false)
        }
    }

    private fun updateUsers(users: List<User>, busyCallback: BusyCallback, isSuccess: MutableLiveData<List<User>>) {
        executor.execute {
            DbService.postsDb.usersDao().insert(users)
            isSuccess.postValue(users)
            busyCallback.setBusy(false)
        }
    }

    private fun updateComments(comments: List<Comment>, isSuccess: MutableLiveData<List<Comment>>) {
        executor.execute {
            DbService.postsDb.commentsDao().insert(comments)
            isSuccess.postValue(comments)
        }
    }

    fun dispose() {
        compositeDisposable.clear()
        executor.awaitTermination(1, TimeUnit.SECONDS)
    }

}