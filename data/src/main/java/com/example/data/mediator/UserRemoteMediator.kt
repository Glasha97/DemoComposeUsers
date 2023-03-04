package com.example.data.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.*
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.data.data_source.LocalUserDataSource
import com.example.data.data_source.RemoteUsersDataSource
import com.example.data.models.user.toEntity
import com.example.database.entity.UserEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val localUserDataSource: LocalUserDataSource,
    private val remoteUsersDataSource: RemoteUsersDataSource
) : RemoteMediator<Int, UserEntity>() {

    private var totalPages = 0
    private var pageIndex = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        return try {

            pageIndex =
                loadType.getPageIndex()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)

            val response = remoteUsersDataSource.getUsers(pageIndex)
            totalPages = response.totalPages ?: 0
            if (loadType == REFRESH) localUserDataSource.refresh(response.users?.map { it.toEntity() }
                ?: emptyList())
            else localUserDataSource.addUsers(response.users?.map { it.toEntity() } ?: emptyList())

            return MediatorResult.Success(
                endOfPaginationReached = pageIndex == totalPages
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private fun LoadType.getPageIndex(): Int? {
        pageIndex = when (this) {
            REFRESH -> 1
            PREPEND -> return null
            APPEND -> ++pageIndex
        }
        return pageIndex
    }

    override suspend fun initialize(): InitializeAction {
        return super.initialize()
    }
}