package com.example.data.models.user.network


import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Long?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("avatar")
    val avatar: String?
)