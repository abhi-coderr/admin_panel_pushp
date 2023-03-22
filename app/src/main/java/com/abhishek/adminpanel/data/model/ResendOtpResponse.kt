package com.oneclick.blackandoneparent.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResendOtpResponse(
    val message:String
):Parcelable