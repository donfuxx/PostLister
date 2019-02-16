package com.appham.postlister.model

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Post::class, User::class, Comment::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun usersDao(): UsersDao
    abstract fun commentsDao(): CommentsDao
}