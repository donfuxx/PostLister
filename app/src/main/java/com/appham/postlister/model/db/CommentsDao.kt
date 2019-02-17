package com.appham.postlister.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.appham.postlister.model.data.Comment

@Dao
interface CommentsDao {

    @Query("SELECT COUNT(id) FROM comments WHERE postId = :postId")
    fun getCount(postId: Int) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<Comment>)

}