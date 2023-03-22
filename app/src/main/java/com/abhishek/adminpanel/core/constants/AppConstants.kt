package com.oneclick.blackandoneparent.core.constants

/**
 * Description: This object is used for the purpose of storing all the app constants at single place.
 *
 * @author Ankit Mishra
 * @since 26/10/21
 */
object AppConstants {

    /**
     * Device Type which is generally 2 for Android.
     */
    const val DEVICE_TYPE = "Android"

    /**
     * Predefined Constants
     */
    const val LANGUAGE="en"
    const val CONTENT_TYPE_JSON_FOR_CALL_API = "application/json; charset=utf-8"
    const val CONTENT_TYPE_JSON_FOR_DEFINE_API = "Content-Type: application/json"
    const val API_USER_TOKEN_KEY = "Authorization"
    const val API_LANGUAGE_KEY = "language"

    /**
     * Firebase Constants
     */
    const val EMAIL_KEY="Email"
    const val USER_NAME_KEY="User Name"


    /**
     * Data store constants
     */
    const val USER_PROFILE_PROTO_DATA_STORE_FILE_NAME = "UserProfile.pb"
    const val PREFERENCE_DATA_STORE_NAME = "coe_preference"

    /**
     * Preference Constants
     */
    const val PREF_DEVICE_TOKEN = "PREF_DEVICE_TOKEN"
    const val PREF_ACCESS_TOKEN = "PREF_ACCESS_TOKEN"
    const val PREF_IS_LANGUAGE_SCREEN_DONE_ONCE = "PREF_IS_LANGUAGE_SCREEN_DONE_ONCE"
    const val PREF_CURRENT_LANGUAGE = "PREF_CURRENT_LANGUAGE"


    /**
     * API Constants
     */
    const val BASE_URL = "https://stage.blackkandonesports.com/api/"


    const val POST_VERIFY_PHONE_NUMBER="verifyPhoneNumber"
    const val POST_OTP_VERIFY="login"
    const val GET_LOGOUT="logout"

    //Intent Key
    const val INTENT_KEY_VERIFY_OTP_SCREEN="INTENT_KEY_VERIFY_OTP_SCREEN"


}
