package com.appham.postlister.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appham.postlister.model.data.Post

@Dao
interface PostsDao {

    @Query("SELECT * FROM posts")
    fun getAll() : List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Post>)

}