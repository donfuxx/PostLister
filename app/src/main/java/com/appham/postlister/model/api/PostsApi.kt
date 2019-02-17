package com.appham.postlister.model.api

import com.appham.postlister.model.data.Post
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * This Api defines how to get posts data
 */
interface PostsApi {

    @GET("/posts")
    fun getPosts(): Observable<List<Post>>

}
