package com.oneclick.blackandoneparent.domain.usecases.authentication

import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.domain.repository.AuthenticationRepository

/**
 * This use case class is used for performing logout.
 *
 * @author Ankit Mishra
 * @since 13-03-2023
 *
 */
class LogoutUseCase(private val authenticationRepository: AuthenticationRepository) {
    suspend fun execute(): Resource<LogoutResponse> = authenticationRepository.logout()
}