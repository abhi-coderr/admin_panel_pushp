package com.oneclick.blackandoneparent.presentation.di

import com.oneclick.blackandoneparent.data.repository.authentication.AuthenticationRepositoryImpl
import com.oneclick.blackandoneparent.data.repository.authentication.datasource.AuthenticationRemoteDataSource

import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Reusable
    fun provideAuthenticationRepository(
        authenticationRemoteDataSource: AuthenticationRemoteDataSource
    ): AuthenticationRepository {
        return AuthenticationRepositoryImpl(
            authenticationRemoteDataSource
        )
    }

}