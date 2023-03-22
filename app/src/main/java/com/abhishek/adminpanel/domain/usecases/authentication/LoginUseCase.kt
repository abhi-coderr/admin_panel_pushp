package com.oneclick.blackandoneparent.domain.usecases.authentication

import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository

/**
 * This use case class is used for performing mobile login.
 *
 * @author Ankit Mishra
 * @since 13-03-23
 *
 */
class LoginUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend fun execute(phoneNumber: String): Resource<VerifyPhoneNumberResponse> =
        authenticationRepository.loginByOtp(phoneNumber)
}