package com.appham.postlister.model.api

import com.appham.postlister.model.data.User
import io.reactivex.Single
import retrofit2.http.GET


/**
 * This Api defines how to get users data
 */
interface UsersApi {

    @GET("/users")
    fun getUsers(): Single<List<User>>
}
