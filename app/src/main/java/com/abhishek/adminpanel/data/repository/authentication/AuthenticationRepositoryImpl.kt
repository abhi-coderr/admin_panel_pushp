package com.oneclick.blackandoneparent.data.repository.authentication

import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse
import com.oneclick.blackandoneparent.data.repository.authentication.datasource.AuthenticationRemoteDataSource
import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository

/**
 * Description: This class provides the implementation of authentication repository methods.
 *
 * @author Ankit Mishra
 * @since 13/03/23
 */
class AuthenticationRepositoryImpl(
    private val authenticationRemoteDataSource: AuthenticationRemoteDataSource
) : AuthenticationRepository {

    override suspend fun loginByOtp(phoneNumber: String): Resource<VerifyPhoneNumberResponse> {
        return authenticationRemoteDataSource.verifyPhoneNumber(phoneNumber)
    }

    override suspend fun logout(): Resource<LogoutResponse> {
        return authenticationRemoteDataSource.logout()
    }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): Resource<VerifyOtpResponse> {
        return authenticationRemoteDataSource.verifyOtp(phoneNumber, otp)
    }

    override suspend fun resendOtp(phoneNumber: String): Resource<VerifyPhoneNumberResponse> {
        return authenticationRemoteDataSource.resendOtp(phoneNumber)
    }

}