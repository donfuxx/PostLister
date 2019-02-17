package com.appham.postlister.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appham.postlister.model.data.Comment
import com.appham.postlister.model.data.Post
import com.appham.postlister.model.data.User

@Database(entities = [Post::class, User::class, Comment::class], version = 1)
abstract class PostsDatabase : RoomDatabase() {
    abstract fun postsDao(): PostsDao
    abstract fun usersDao(): UsersDao
    abstract fun commentsDao(): CommentsDao
}