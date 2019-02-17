package com.appham.postlister.model.db

import android.app.Application
import androidx.room.Room
import com.appham.postlister.PostListerApp

object DbService {

    val postsDb by lazy {
        createPostsDb(PostListerApp.app)
    }

    private fun createPostsDb(applicationContext: Application): PostsDatabase {
        return Room.databaseBuilder(
            applicationContext,
            PostsDatabase::class.java, "posts_database"
        ).build()
    }
}