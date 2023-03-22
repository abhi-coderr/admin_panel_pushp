package com.oneclick.blackandoneparent.presentation.di

import com.oneclick.blackandoneparent.core.network_helpers.ApiHelper
import com.oneclick.blackandoneparent.core.utils.AppPreference
import com.oneclick.blackandoneparent.data.api.ApiService
import com.oneclick.blackandoneparent.data.repository.authentication.datasource.AuthenticationRemoteDataSource
import com.oneclick.blackandoneparent.data.repository.authentication.datasourceimpl.AuthenticationRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideAuthenticationRemoteDataSource(
        apiService: ApiService,
        appPreference: AppPreference,
        apiHelper: ApiHelper,
    ): AuthenticationRemoteDataSource {
        return AuthenticationRemoteDataSourceImpl(
            apiService,
            appPreference,
            apiHelper
        )
    }

}