package com.example.data.data_source

import androidx.paging.PagingSource
import com.example.database.entity.UserEntity

interface LocalUserDataSource {

    suspend fun addUsers(users: List<UserEntity>)
    suspend fun refresh(users: List<UserEntity>)
    suspend fun getUserById(id: Long): UserEntity?
    fun userPagingSource(): PagingSource<Int, UserEntity>
    suspend fun updateIsFavourite(isFavourite: Boolean, userId: Long)
    suspend fun getFavouriteUsers(): List<UserEntity>

}