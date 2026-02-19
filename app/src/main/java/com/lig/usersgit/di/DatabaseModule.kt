package com.lig.usersgit.di

import android.content.Context
import androidx.room.Room
import com.lig.usersgit.data.local.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "github_users_db"
    ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase) = database.userDao()
}