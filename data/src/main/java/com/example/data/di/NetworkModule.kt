package com.example.data.di

import com.example.data.data_source.LocalUserDataSource
import com.example.data.data_source.LocalUserDataSourceImpl
import com.example.data.data_source.RemoteUsersDataSource
import com.example.data.data_source.RemoteUsersDataSourceImpl
import com.example.data.repository.UsersRepository
import com.example.data.repository.UsersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun remoteUsersDataSource(source: RemoteUsersDataSourceImpl): RemoteUsersDataSource

    @Binds
    @Singleton
    abstract fun localUsersDataSource(source: LocalUserDataSourceImpl): LocalUserDataSource

    @Binds
    @Singleton
    abstract fun usersRepository(repo: UsersRepositoryImpl): UsersRepository
}