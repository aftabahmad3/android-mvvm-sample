package com.mobile.sample.data.users.local

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.mobile.sample.data.users.User
import com.mobile.sample.data.users.local.UserDao.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class UserLocalModel(
        @PrimaryKey @ColumnInfo(name = "id") override var id: Int,
        @ColumnInfo(name = "name") override var name: String,
        @ColumnInfo(name = "username") override var username: String
) : User