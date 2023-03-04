package com.example.data.models.user.ui


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Long = -1,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val isFavourite: Boolean = false
) : Parcelable