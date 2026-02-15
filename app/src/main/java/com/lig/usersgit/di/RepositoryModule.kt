package com.lig.usersgit.di

import com.lig.usersgit.data.remote.repo.UserRepositoryRemote
import com.lig.usersgit.domain.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindUserRepository(impl: UserRepositoryRemote): UserRepository

}