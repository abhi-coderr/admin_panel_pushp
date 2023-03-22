package com.oneclick.blackandoneparent.data.repository.authentication.datasource

import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.blackandoneparent.data.model.ResendOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse


/**
 * This data source interface is used for defining authentication related api operations.
 *
 * @author Ankit Mishra
 * @since 18-07-2022
 */
interface AuthenticationRemoteDataSource {
    /**
     * This method calls the api for login by verifying phone number.
     * @param phoneNumber Provide the user phone number.
     */
    suspend fun verifyPhoneNumber(phoneNumber: String): Resource<VerifyPhoneNumberResponse>


    /**
     * This method calls the api for logout.
     */
    suspend fun logout(): Resource<LogoutResponse>


    /**
     * This method calls the api for verifying otp.
     * @param phoneNumber Provide the phone number.
     * @param otp Provide the user email.
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