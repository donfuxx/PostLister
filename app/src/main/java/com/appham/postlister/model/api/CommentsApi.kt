package com.appham.postlister.model.api

import com.appham.postlister.model.data.Comment
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * This Api defines how to get comments data
 */
interface CommentsApi {

    @GET("/comments")
    fun getComments(): Observable<List<Comment>>

}
