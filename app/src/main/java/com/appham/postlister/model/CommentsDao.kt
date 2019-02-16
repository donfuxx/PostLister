package com.appham.postlister.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentsDao {

    @Query("SELECT COUNT(id) FROM comments WHERE email = :email")
    fun getCount(email: String) : Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<Comment>)

}