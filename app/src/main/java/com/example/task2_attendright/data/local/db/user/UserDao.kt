package com.example.task2_attendright.data.local.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE userId = :id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?

    @Update
    suspend fun updateUser(userEntity: UserEntity)
}