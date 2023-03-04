package com.example.data.data_source

import com.example.data.UserApi
import com.example.data.models.user.network.UsersResponse
import javax.inject.Inject

class RemoteUsersDataSourceImpl @Inject constructor(private val api: UserApi) :
    RemoteUsersDataSource {

    override suspend fun getUsers(page: Int): UsersResponse = api.getUsers(page)

}