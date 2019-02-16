package com.appham.postlister.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UsersDao {

    @Query("SELECT * FROM users WHERE id == :id")
    fun getById(id: Int) : User

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(users: List<User>)

}