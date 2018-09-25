package com.mobile.sample.data.users.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import android.arch.persistence.room.Transaction
import com.mobile.sample.data.users.User
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.coroutineScope

@Dao
abstract class UserDao {

    companion object {
        const val TABLE_NAME = "users"
    }

    @Query("SELECT * from $TABLE_NAME")
    abstract fun getUsers(): List<UserLocalModel>

    @Query("SELECT * from $TABLE_NAME WHERE id = :userId")
    abstract fun getUser(userId: Int): UserLocalModel

    @Insert(onConflict = REPLACE)
    abstract fun insert(user: UserLocalModel)

    @Query("DELETE from $TABLE_NAME")
    abstract fun deleteAll()

    @Transaction
    open fun insertUsers(users: List<User>) {
        deleteAll()
        users.forEach {
            val userDb = UserLocalModel(it.id, it.name, it.username)
            insert(userDb)
        }
    }

    suspend fun getUsersAsync() = coroutineScope {
        async { getUsers() }.await()
    }

    suspend fun getUserAsync(id: Int) = coroutineScope {
        async { getUser(id) }.await()
    }
}