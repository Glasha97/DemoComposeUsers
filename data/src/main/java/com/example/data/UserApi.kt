package com.example.data

import com.example.data.models.user.network.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface UserApi {

    @GET("api/users")
    suspend fun getUsers(@Query("page") page: Int): UsersResponse
}