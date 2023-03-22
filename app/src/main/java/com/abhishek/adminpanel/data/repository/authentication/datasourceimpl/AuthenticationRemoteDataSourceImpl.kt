package com.oneclick.blackandoneparent.data.repository.authentication.datasourceimpl

import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.core.network_helpers.ApiHelper
import com.oneclick.blackandoneparent.core.utils.AppPreference
import com.oneclick.blackandoneparent.core.utils.getAccessTokenWithoutPrefix
import com.oneclick.blackandoneparent.core.utils.getRequestBody
import com.oneclick.blackandoneparent.data.api.ApiService
import com.oneclick.blackandoneparent.data.model.LogoutResponse
import com.oneclick.blackandoneparent.data.model.VerifyOtpResponse
import com.oneclick.blackandoneparent.data.model.VerifyPhoneNumberResponse
import com.oneclick.blackandoneparent.data.repository.authentication.datasource.AuthenticationRemoteDataSource
import org.json.JSONObject


/**
 * This data source interface is used for defining language related api operations.
 *
 * @author Ankit Mishra
 * @since 18-07-2022
 */
class AuthenticationRemoteDataSourceImpl(
    private val apiService: ApiService,
    private val appPreference: AppPreference,
    private val apiHelper: ApiHelper
) : AuthenticationRemoteDataSource {

    override suspend fun verifyPhoneNumber(phoneNumber: String): Resource<VerifyPhoneNumberResponse> {
        //Calling api and returning response.
        return apiHelper.callApi {
            apiService.doVerifyMobileNumber(
                JSONObject().apply {
                    put("phone_no", phoneNumber.trim())
                }.getRequestBody()
            )
        }
    }


    override suspend fun logout(): Resource<LogoutResponse> {
        //Calling api and returning response.
        return apiHelper.callApi {
            apiService.logout(appPreference.getAccessTokenWithoutPrefix())
        }
    }

    override suspend fun verifyOtp(phoneNumber: String, otp: String): Resource<VerifyOtpResponse> {
        //Calling api and returning response.
        return apiHelper.callApi {
            apiService.verifyOtp(JSONObject().apply {
                put("phone_no", phoneNumber.trim())
                put("otp_code", otp.trim())
                put("device_id", "testing testing")
            }.getRequestBody())
        }
    }

    override suspend fun resendOtp(phoneNumber: String): Resource<VerifyPhoneNumberResponse> {
        //Calling api and returning response.
        return apiHelper.callApi {
            apiService.doVerifyMobileNumber(
                JSONObject().apply {
                    put("phone_no", phoneNumber.trim())
                }.getRequestBody()
            )
        }
    }
}