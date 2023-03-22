package com.oneclick.blackandoneparent.domain.usecases.authentication

import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository

/**
 * This use case class is used for verifying otp.
 *
 * @author Ankit Mishra
 * @since 13-03-2023
 *
 */
class VerifyOtpUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend fun execute(
        phoneNumber: String,
        otp: String
    ): Resource<VerifyOtpResponse> =
        authenticationRepository.verifyOtp(phoneNumber, otp)
}