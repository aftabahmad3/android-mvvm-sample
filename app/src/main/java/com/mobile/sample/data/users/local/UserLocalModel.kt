package com.mobile.sample.data.users.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.local.UserDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserLocalModel(
        @PrimaryKey @ColumnInfo(name = "id") override var id: Int,
        @ColumnInfo(name = "name") override var name: String,
        @ColumnInfo(name = "username") override var username: String
) : User