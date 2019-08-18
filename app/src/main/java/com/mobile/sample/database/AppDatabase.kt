package com.mobile.sample.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mobile.sample.data.users.local.UserDao
import com.mobile.sample.data.users.local.UserLocalModel

@Database(entities = [(UserLocalModel::class)], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}