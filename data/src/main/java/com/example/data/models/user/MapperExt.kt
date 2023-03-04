package com.example.data.models.user

import com.example.data.models.user.network.UserResponse
import com.example.data.models.user.ui.User
import com.example.database.entity.UserEntity

fun UserResponse.toUi() = User(
    id = id ?: -1,
    email = email ?: "",
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    avatar = avatar ?: "",
)

fun UserResponse.toEntity() = UserEntity(
    id = id ?: -1,
    email = email ?: "",
    firstName = firstName ?: "",
    lastName = lastName ?: "",
    avatar = avatar ?: "",
)

fun UserEntity.toUi() = User(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    avatar = avatar,
    isFavourite = isFavourite
)