package com.appham.postlister.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "comments")
data class Comment(

    @SerializedName("body")
    val body: String,

    @SerializedName("email")
    val email: String,

    @PrimaryKey
    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("postId")
    val postId: Int
)