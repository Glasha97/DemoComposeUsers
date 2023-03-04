package com.example.data.data_source

import com.example.data.models.user.network.UsersResponse


interface RemoteUsersDataSource {

    suspend fun getUsers(page: Int): UsersResponse
}