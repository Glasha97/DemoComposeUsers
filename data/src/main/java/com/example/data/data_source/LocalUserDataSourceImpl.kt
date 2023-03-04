package com.example.data.data_source

import androidx.paging.PagingSource
import com.example.database.dao.UserDao
import com.example.database.entity.UserEntity
import javax.inject.Inject

class LocalUserDataSourceImpl @Inject constructor(private val userDao: UserDao) :
    LocalUserDataSource {
    override suspend fun addUsers(users: List<UserEntity>) {
        userDao.insertAll(users)
    }

    override suspend fun refresh(users: List<UserEntity>) {
        userDao.refresh(users)
    }

    override suspend fun getUserById(id: Long): UserEntity? =
        userDao.getUsedById(id)

    override fun userPagingSource(): PagingSource<Int, UserEntity> =
        userDao.userPagingSource()

    override suspend fun updateIsFavourite(isFavourite: Boolean, userId: Long) =
        userDao.updateIsFavourite(isFavourite, userId)

    override suspend fun getFavouriteUsers(): List<UserEntity> = userDao.getFavouriteUsers()

}
