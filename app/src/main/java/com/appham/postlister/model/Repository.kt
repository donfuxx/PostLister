package com.appham.postlister.model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.appham.postlister.model.api.ApiService
import com.appham.postlister.model.data.Comment
import com.appham.postlister.model.data.Post
import com.appham.postlister.model.data.User
import com.appham.postlister.model.db.DbService
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


    fun posts(isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<List<Post>>) {

        isBusy.postValue(true)

        val disposable =
            ApiService.postsApi.getPosts()
            .subscribeOn(Schedulers.io())
            .subscribeBy(

                onSuccess = {
                    Log.d(javaClass.simpleName, "onSuccess: $it")
                    updatePosts(it, isSuccess)
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

    fun users(isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<List<User>>) {
        isBusy.postValue(true)

        val disposable =
            ApiService.usersApi.getUsers()
                .subscribeOn(Schedulers.io())
                .subscribeBy(

                    onSuccess = {
                        Log.d(javaClass.simpleName, "onSuccess: $it")
                        updateUsers(it, isSuccess)
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

    fun user(id: Int, isBusy: MutableLiveData<Boolean>, isSuccess: MutableLiveData<User>) {
        val users: MutableLiveData<List<User>> = MutableLiveData()
        users(isBusy, users)

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

    private fun updatePosts(posts: List<Post>, isSuccess: MutableLiveData<List<Post>>) {
        executor.execute {
            DbService.postsDb.postsDao().insert(posts)
            isSuccess.postValue(posts)
        }
    }

    private fun updateUsers(users: List<User>, isSuccess: MutableLiveData<List<User>>) {
        executor.execute {
            DbService.postsDb.usersDao().insert(users)
            isSuccess.postValue(users)
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