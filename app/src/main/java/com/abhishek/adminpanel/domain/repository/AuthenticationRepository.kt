package com.oneclick.blackandoneparent.domain.repository

import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.blackandoneparent.data.model.ResendOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse

/**
 * Description: This interface is used for the purpose of defining authentication repository methods.
 *
 * @author Ankit Mishra
 * @since 22/07/22
 */
interface AuthenticationRepository {

    /**
     * This method calls the api for login.
     * @param phoneNumber Provide the phoneNumber of user.
     */
    suspend fun loginByOtp(phoneNumber: String): Resource<VerifyPhoneNumberResponse>

    /**
     * This method calls the api for logout.
     */
    suspend fun logout(): Resource<LogoutResponse>


    /**
     * This method calls the api for verifying otp.
     * @param phoneNumber Provide the phone number.
     * @param otp Provide the otp.
     */
    suspend fun verifyOtp(
        phoneNumber: String,
        otp: String
    ): Resource<VerifyOtpResponse>

    /**
     * This method calls the api for resending otp.
     * @param phoneNumber Provide the user email.
     */
    suspend fun resendOtp(phoneNumber: String): Resource<VerifyPhoneNumberResponse>


}