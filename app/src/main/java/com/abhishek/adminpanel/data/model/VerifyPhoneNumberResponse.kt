package com.oneclick.blackandoneparent.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VerifyPhoneNumberResponse(
    val status: Int,
    val message: String,
    @SerializedName("data") val otp: Long
) : Parcelable