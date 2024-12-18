package com.example.task2_attendright.data.local.db.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.task2_attendright.data.local.db.user.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table WHERE userId = :id LIMIT 1")
    suspend fun getUserById(id: String): UserEntity?

    @Query("SELECT * FROM user_table WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUserByEmailPassword(email: String, password: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)


}
