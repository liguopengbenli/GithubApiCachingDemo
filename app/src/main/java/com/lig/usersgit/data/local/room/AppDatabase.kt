package com.lig.usersgit.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lig.usersgit.data.local.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}