package com.oneclick.blackandoneparent.core

import android.app.Application
import android.graphics.Typeface
import com.oneclick.blackandoneparent.core.custom_views.validators.MyValidations
import com.oneclick.blackandoneparent.core.network_helpers.NetworkMonitor
import dagger.hilt.android.HiltAndroidApp

/**
 * Description: This application class is used for the purpose of defining common application wide functionality.
 *
 * @author Ankit Mishra
 * @since 13/03/23
 */
@HiltAndroidApp
class MyApplication : Application() {

    companion object {

        /**
         * To check internet is available or not.
         */
        var isInternetAvailable: Boolean = false


        var mTypeFactory: TypeFactory? = null
        lateinit var application: Application

        fun getTypeFace(type: TypeFactory.FontType): Typeface {
            return when (type) {
                TypeFactory.FontType.REGULAR -> mTypeFactory!!.regular
                TypeFactory.FontType.MEDIUM -> mTypeFactory!!.medium
                TypeFactory.FontType.SEMI_BOLD -> mTypeFactory!!.semibold
            }
        }

    }

    override fun onCreate() {
        super.onCreate()

        application = this

        /**
         * Setting up the validation.
         */
        MyValidations.setAllEditTextValidations(this)

        mTypeFactory = TypeFactory(this)

        /**
         * Start network callback
         */
        NetworkMonitor(this).startNetworkCallback()
    }

    override fun onTerminate() {
        super.onTerminate()
        /**
         * Stop network callback
         */
        NetworkMonitor(this).stopNetworkCallback()
    }
}