package com.example.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.example.database.BaseDao
import com.example.database.entity.UserEntity

@Dao
abstract class UserDao : BaseDao<UserEntity>() {

    @Query("SELECT * FROM userentity WHERE id = :userId")
    abstract suspend fun getUsedById(userId: Long): UserEntity?

    @Query("SELECT * FROM userentity")
    abstract fun userPagingSource(): PagingSource<Int, UserEntity>

    @Query("DELETE FROM userentity")
    abstract fun deleteAll()

    @Query("UPDATE userentity SET isFavourite = :isFavourite WHERE id = :userId")
    abstract suspend fun updateIsFavourite(isFavourite: Boolean, userId: Long)

    @Query("UPDATE userentity SET email = :email, firstName = :firstName, lastName = :lastName, avatar = :avatar WHERE id = :userId")
    abstract suspend fun updateUserExceptFavourite(
        userId: Long,
        email: String,
        firstName: String,
        lastName: String,
        avatar: String,
    )

    @Query("SELECT * FROM userentity WHERE isFavourite = 1")
    abstract suspend fun getFavouriteUsers():List<UserEntity>

    @Transaction
    open suspend fun refresh(users: List<UserEntity>) {
        users.forEach { user ->
            if (getUsedById(user.id) == null) {
                insertAll(user)
            } else {
                updateUserExceptFavourite(
                    userId = user.id,
                    email = user.email,
                    firstName = user.firstName,
                    lastName = user.lastName,
                    avatar = user.avatar
                )
            }
        }
    }
}
