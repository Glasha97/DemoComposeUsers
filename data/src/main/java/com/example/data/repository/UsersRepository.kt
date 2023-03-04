package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.models.user.ui.User
import com.example.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow

interface UsersRepository {

    suspend fun getUsers(page: Int): List<User>
    suspend fun getUserById(userId: Long): User?
    fun getUserPaging(): Flow<PagingData<UserEntity>>
    suspend fun updateIsFavourite(isFavourite: Boolean, userId: Long)
    suspend fun getFavouriteUsers(): List<User>
}