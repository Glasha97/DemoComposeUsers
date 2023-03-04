package com.example.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: Long,
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val isFavourite: Boolean = false
)
