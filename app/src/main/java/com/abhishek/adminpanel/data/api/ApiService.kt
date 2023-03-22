package com.oneclick.blackandoneparent.data.api


import com.oneclick.blackandoneparent.core.network_helpers.Authenticated
import com.oneclick.blackandoneparent.core.constants.AppConstants
import com.oneclick.blackandoneparent.core.constants.AppConstants.CONTENT_TYPE_JSON_FOR_DEFINE_API
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.blackandoneparent.data.model.ResendOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse
import okhttp3.RequestBody
import retrofit2.http.*

/**
 * Description: This interface is used for the purpose of defining retrofit api call methods.
 *
 * @author Ankit Mishra
 * @since 20/10/21
 */
interface ApiService {
    @POST(AppConstants.POST_VERIFY_PHONE_NUMBER)
    suspend fun doVerifyMobileNumber(
        @Body requestBody: RequestBody
    ): VerifyPhoneNumberResponse


    @POST(AppConstants.POST_OTP_VERIFY)
    suspend fun verifyOtp(
        @Body requestBody: RequestBody
    ): VerifyOtpResponse

    @Headers(CONTENT_TYPE_JSON_FOR_DEFINE_API)
    @PUT(AppConstants.GET_LOGOUT)
    @Authenticated
    suspend fun logout(@Query("token")  token:String): LogoutResponse

}