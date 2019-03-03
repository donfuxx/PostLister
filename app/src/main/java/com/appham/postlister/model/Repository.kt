package com.appham.postlister.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.model.api.ApiService
import com.appham.postlister.model.data.Comment
import com.appham.postlister.model.data.Post
import com.appham.postlister.model.data.User
import com.appham.postlister.model.db.DbService
import com.appham.postlister.viewmodel.BusyCallback
import com.appham.postlister.viewmodel.CommentsLoadedCallback
import com.appham.postlister.viewmodel.PostsLoadedCallback
import com.appham.postlister.viewmodel.UserLoadedCallback
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

    fun user(id: Int, busyCallback: BusyCallback, userLoadedCallback: UserLoadedCallback) {
        val users: MutableLiveData<List<User>> = MutableLiveData()
        users(busyCallback, users)

        users.observeForever { userList ->
            userList?.takeIf { it.isNotEmpty() }.let {
                executor.execute {
                    userLoadedCallback.setUser(DbService.postsDb.usersDao().getById(id))
                }
            }
        }
    }

    fun comments(busyCallback: BusyCallback, isSuccess: MutableLiveData<List<Comment>>) {
        busyCallback.setBusy(true)

        val disposable =
            ApiService.commentsApi.getComments()
                .subscribeOn(Schedulers.io())
                .subscribeBy(

                    onSuccess = {
                        Log.d(javaClass.simpleName, "onSuccess: $it")
                        updateComments(it, busyCallback, isSuccess)
                    },

                    onError = {
                        Log.e(javaClass.simpleName, "onError: $it")
                        isSuccess.postValue(null)
                        busyCallback.setBusy(false)
                    }

                )

        compositeDisposable.add(disposable)
    }

    fun commentsCount(postId: Int, busyCallback: BusyCallback, commentsLoadedCallback: CommentsLoadedCallback) {
        val comments: MutableLiveData<List<Comment>> = MutableLiveData()
        comments(busyCallback, comments)

        comments.observeForever { commentList ->
            commentList?.takeIf { it.isNotEmpty() }.let {
                executor.execute {
                    commentsLoadedCallback.setComments(DbService.postsDb.commentsDao().getCount(postId))
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

    private fun updateComments(comments: List<Comment>, busyCallback: BusyCallback, isSuccess: MutableLiveData<List<Comment>>) {
        executor.execute {
            DbService.postsDb.commentsDao().insert(comments)
            isSuccess.postValue(comments)
            busyCallback.setBusy(false)
        }
    }

    fun dispose() {
        compositeDisposable.clear()
        executor.awaitTermination(1, TimeUnit.SECONDS)
    }

}