package com.appham.postlister.model.api

import com.appham.postlister.model.data.User
import io.reactivex.Observable
import retrofit2.http.GET


/**
 * This Api defines how to get users data
 */
interface UsersApi {

    @GET("/users")
    fun getUsers(): Observable<List<User>>
}
