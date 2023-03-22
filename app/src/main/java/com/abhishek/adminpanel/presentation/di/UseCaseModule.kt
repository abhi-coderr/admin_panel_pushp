package com.oneclick.blackandoneparent.presentation.di


import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository
import com.oneclick.blackandoneparent.domain.usecases.authentication.LoginUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.LogoutUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.ResendOtpUseCase
import com.oneclick.blackandoneparent.domain.usecases.authentication.VerifyOtpUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    fun provideLogoutUseCase(authenticationRepository: AuthenticationRepository): LogoutUseCase {
        return LogoutUseCase(authenticationRepository)
    }

    @Provides
    fun provideLoginUseCase(authenticationRepository: AuthenticationRepository): LoginUseCase {
        return LoginUseCase(authenticationRepository)
    }


    @Provides
    fun provideResendOtpUseCase(authenticationRepository: AuthenticationRepository): ResendOtpUseCase {
        return ResendOtpUseCase(authenticationRepository)
    }

    @Provides
    fun provideVerifyOtpUseCase(authenticationRepository: AuthenticationRepository): VerifyOtpUseCase {
        return VerifyOtpUseCase(authenticationRepository)
    }

}