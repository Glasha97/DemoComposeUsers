package com.example.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.data.data_source.LocalUserDataSource
import com.example.data.data_source.RemoteUsersDataSource
import com.example.data.mediator.UserRemoteMediator
import com.example.data.models.user.toEntity
import com.example.data.models.user.toUi
import com.example.data.models.user.ui.User
import com.example.database.dao.UserDao
import com.example.database.entity.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class UsersRepositoryImpl @Inject constructor(
    private val remoteUsersDataSource: RemoteUsersDataSource,
    private val localDataSource: LocalUserDataSource,
) : UsersRepository {
    override suspend fun getUsers(page: Int): List<User> {
        val users = remoteUsersDataSource.getUsers(page)
        localDataSource.addUsers(users.users?.map { it.toEntity() } ?: emptyList())
        return users.users?.map { it.toUi() } ?: emptyList()
    }

    override suspend fun getUserById(userId: Long): User? {
        return localDataSource.getUserById(userId)?.toUi()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getUserPaging(): Flow<PagingData<UserEntity>> = Pager(
        config = PagingConfig(10),
        initialKey = 0,
        remoteMediator = UserRemoteMediator(localDataSource, remoteUsersDataSource),
        pagingSourceFactory = { localDataSource.userPagingSource() }
    ).flow

    override suspend fun updateIsFavourite(isFavourite: Boolean, userId: Long) {
        localDataSource.updateIsFavourite(isFavourite, userId)
    }

    override suspend fun getFavouriteUsers(): List<User> =
        localDataSource.getFavouriteUsers().map { it.toUi() }

}