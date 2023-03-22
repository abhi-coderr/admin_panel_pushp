package com.oneclick.blackandoneparent.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class LogoutResponse(
    val message:String
):Parcelable