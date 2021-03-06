package com.appham.postlister.model.api

import com.appham.postlister.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


object ApiService {

    private const val BASE_URL = BuildConfig.BASE_URL;

    /**
     * Create api interface for posts api
     */
    val postsApi: PostsApi by lazy {
        retrofit.create(PostsApi::class.java)
    }

    /**
     * Create api interface for users api
     */
    val usersApi: UsersApi by lazy {
        retrofit.create(UsersApi::class.java)
    }

    /**
     * Create api interface for comments api
     */
    val commentsApi: CommentsApi by lazy {
        retrofit.create(CommentsApi::class.java)
    }

    /**
     * Http client with logging enabled
     */
    private val okHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Retrofit instance with Rxjava adapter
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }


}