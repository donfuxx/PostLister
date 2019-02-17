package com.appham.postlister.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "posts")
data class Post(

    @SerializedName("body")
    val body: String,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("title")
    val title: String,

    @SerializedName("userId")
    val userId: Int
)