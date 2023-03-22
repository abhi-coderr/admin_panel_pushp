package com.oneclick.blackandoneparent.data.model.intent

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * Description: This data class is used for passing the data in verify otp screen. Here IM stands for intent model.
 *
 * @author Ankit Mishra
 * @since 21/03/23
 */
@Parcelize
data class VerifyOtpIM(
    val phoneNumber: String,
    val otp: String
):Parcelable