package com.oneclick.blackandoneparent.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class VerifyOtpResponse(
    @SerializedName("message") var message: String? = null,
    @SerializedName("access_token") var data: @RawValue ArrayList<Data> = arrayListOf(),
    @SerializedName("token") var token: String? = null,
    @SerializedName("status") var status: Int? = null
) : Parcelable

@Parcelize
data class Data(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("phone_no") var phoneNo: String? = null,
    @SerializedName("gender") var gender: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("joining_date") var joiningDate: String? = null,
    @SerializedName("is_app_notification") var isAppNotification: Int? = null
) : Parcelable

/**
 * Description: This enum class is used for the purpose of differentiating between different type of otp requests.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
enum class OtpType(val key: String) {
    LOGIN("LOGIN"),
    REGISTER("REGISTER"),
    FORGOT_PASSWORD("FORGOTPASSWORD")
}

/**
 * Description: This enum class is used for the purpose of differentiating between different type of otp requests.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
enum class OtpPurpose {
    API_BASED_OPERATION,
    FIREBASE_MOBILE_LOGIN
}