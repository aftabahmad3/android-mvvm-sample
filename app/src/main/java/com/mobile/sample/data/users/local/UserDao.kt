package com.mobile.sample.data.users.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import com.mobile.sample.data.users.User

@Dao
abstract class UserDao {

    companion object {
        const val TABLE_NAME = "users"
    }

    @Query("SELECT * from $TABLE_NAME")
    abstract suspend fun getUsers(): List<UserLocalModel>

    @Query("SELECT * from $TABLE_NAME WHERE id = :userId")
    abstract suspend fun getUser(userId: Int): UserLocalModel

    @Insert(onConflict = REPLACE)
    abstract suspend fun insert(user: UserLocalModel)

    @Query("DELETE from $TABLE_NAME")
    abstract fun deleteAll()

    @Transaction
    open suspend fun insertUsers(users: List<User>) {
        deleteAll()
        users.forEach {
            val userDb = UserLocalModel(it.id, it.name, it.username)
            insert(userDb)
        }
    }
}