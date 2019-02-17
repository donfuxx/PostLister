package com.appham.postlister.model.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User(

    @Ignore
    @SerializedName("address")
    val address: Address? = null,

    @Ignore
    @SerializedName("company")
    val company: Company? = null,

    @SerializedName("email")
    var email: String = "",

    @PrimaryKey
    @SerializedName("id")
    var id: Int = 0,

    @SerializedName("name")
    var name: String = "",

    @SerializedName("phone")
    var phone: String = "",

    @SerializedName("username")
    var username: String = "",

    @SerializedName("website")
    var website: String = ""
)