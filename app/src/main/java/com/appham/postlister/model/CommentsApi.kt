package com.appham.postlister.model

import io.reactivex.Observable
import retrofit2.http.GET

/**
 * This Api defines how to get comments data
 */
interface CommentsApi {

    @GET("/comments")
    fun getComments(): Observable<List<Comment>>

}
