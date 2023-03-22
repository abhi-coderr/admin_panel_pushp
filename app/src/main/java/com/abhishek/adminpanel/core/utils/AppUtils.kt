package com.oneclick.blackandoneparent.core.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityManager
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.*
import android.os.Build.VERSION.SDK_INT
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.google.firebase.messaging.FirebaseMessaging
import com.oneclick.androidcoe.core.network_helpers.Resource
import com.oneclick.blackandoneparent.R

import com.oneclick.blackandoneparent.core.constants.AppConstants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.File
import java.net.Inet4Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.util.*


/**
 * This method is used for the purpose of executing function after some time.
 * Useful fot the functions which changes the ui too much or too many times.
 * @author Ankit Mishra
 * @since 12/10/21
 *
 * @param timeInMillis Provide delay time in milliseconds
 * @param function Provide function which should be executed after the supplied delay.
 *
 * @return Unit
 */
inline fun executeAfterSomeTime(timeInMillis: Long = 200, crossinline function: () -> Unit) {
    ifThrowsPrintStackTrace {
        Handler(Looper.getMainLooper()).postDelayed({ function() }, timeInMillis)
    }
}


/**
 * Description: This method is used for the purpose of reducing same try catch blocks. Using inline function also saves the memory.
 * @author Ankit Mishra
 * @param function Provide function for which the try/catch is required.
 * @since 19th May, 2021
 * @return Unit
 */
inline fun ifThrowsPrintStackTrace(function: () -> Unit) {
    try {
        function()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * Description: This method is used for the purpose of hiding soft keyboard.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 */
fun AppCompatActivity.hideKeyboard() {
    val inputManager: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        currentFocus?.windowToken,
        InputMethodManager.SHOW_FORCED
    )
}

/**
 * Description: This method is used for the purpose of showing soft keyboard.
 *
 * [NOTE: If you are using this in onCreate() then kindly add some delay.]
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param view Provide the view for which the keyboard should be shown. Generally, this will be edit text.
 */
fun AppCompatActivity.showKeyboard(view: View) {
    val inputManager: InputMethodManager =
        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.showSoftInput(view, 0)
}


/**
 * Description: This method is used for the purpose of getting capitalized string for provided string.
 *
 * Replacement for Kotlin's deprecated `capitalize()` function.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 */
fun String.capitalized(): String {
    return this.replaceFirstChar {
        if (it.isLowerCase())
            it.titlecase(Locale.getDefault())
        else it.toString()
    }
}


/**
 * Description: This method is used for the purpose of checking if the app is in background or not.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 */
fun isAppIsInBackground(context: Context): Boolean {
    var isInBackground = true
    val am =
        context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningProcesses =
        am.runningAppProcesses
    for (processInfo in runningProcesses) {
        if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
            for (activeProcess in processInfo.pkgList) {
                if (activeProcess == context.packageName) {
                    isInBackground = false
                }
            }
        }
    }
    return isInBackground
}

/**
 * Description: This class is used for the purpose of creating a safe click.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param defaultInterval Provide the interval for which the click shouldn't work. By default, it is 700 milliseconds.
 * @param onSafeCLick Provide the click which should be executed if the difference between the last click and current time is less than the provided interval.
 *
 */
private class SafeClickListener(
    private var defaultInterval: Int = 700,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

/**
 * Description: This method is used for the purpose of setting the on click listener which will be executed once on every click performed during provided interval.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param minimumIntervalBeforeNextClick Provide the interval for which the click shouldn't work. By default, it is 700 milliseconds.
 * @param onClick Provide the click which should be executed if the difference between the last click and current time is less than the provided interval.
 */

fun View.setSafeOnClickListener(
    minimumIntervalBeforeNextClick: Int = 700,
    onClick: (View) -> Unit
) {
    val safeClickListener = SafeClickListener(minimumIntervalBeforeNextClick) {
        onClick(it)
    }
    setOnClickListener(safeClickListener)
}


/**
 * Description: This method is used for the purpose of generating firebase token.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param onSuccess Provide the success callback.
 * @param onFailure Provide the failure callback.
 */
fun generateFirebaseDeviceToken(
    onSuccess: (token: String) -> Unit,
    onFailure: (exception: Exception) -> Unit
) {
    FirebaseMessaging.getInstance().token.addOnCompleteListener {

        if (it.isComplete) {
            try {
                val newToken = it.result.toString()
                onSuccess(newToken)
            } catch (e: Exception) {
                onFailure(e)
            }
        }
    }
}

/**
 * Description: This method is used for the purpose of setting up the html data in provided web view.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param webView Provide the web view in which you want to present the html data.
 * @param htmlData Provide the html data which you want to present in the web view.
 */
@SuppressLint("SetJavaScriptEnabled")
fun setHtmlDataInWebView(webView: WebView, htmlData: String) {
    webView.apply {
        webViewClient = WebViewClient()
        settings.apply {
            setSupportZoom(true)
            javaScriptEnabled = true
        }

        loadDataWithBaseURL(
            null,
            htmlData,
            "text/html",
            "utf-8",
            null
        )
    }
}

/**
 * Description: This method is used for the purpose of opening browser with specified url.
 * @author Ankit Mishra
 * @since 29/11/21
 * @param strLoadingUrl Provide url which you want to open in the browser.
 * @return Unit
 */
fun Context.openBrowser(strLoadingUrl: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(strLoadingUrl))
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    try {
        startActivity(intent)
    } catch (ex: ActivityNotFoundException) {
        intent.setPackage(null)
        startActivity(Intent.createChooser(intent, "Select Browser"))
    }
}


/**
 * Description: This method is used for the purpose of changing password visibility.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param showHideImageView Provide show/hide eye image view to toggle.
 * @param passwordEditText Provide edit text in which it is required to hide or show text.
 * @param eyeDrawableResource Provide the eye drawable resource.
 * @param crossedEyeDrawableResource Provide the crossed eye drawable resource.
 *
 * @return Unit
 */
fun changePasswordVisibility(
    passwordEditText: EditText,
    showHideImageView: ImageView,
    @DrawableRes eyeDrawableResource: Int,
    @DrawableRes crossedEyeDrawableResource: Int
) {
    if (passwordEditText.transformationMethod == PasswordTransformationMethod.getInstance()
    ) {
        showHideImageView.setImageResource(crossedEyeDrawableResource)
        //Show Password
        passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
    } else {
        showHideImageView.setImageResource(eyeDrawableResource)
        //Hide Password
        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
    }
    //Setting the cursor to the end.
    passwordEditText.setSelection(passwordEditText.text!!.length)
}


/**
 * Description: This enum class is used for the purpose of getting sharable url for different kind of socials.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 */
enum class SocialTypeKey(
    private val prefix: String,
) {
    TWITTER("https://twitter.com/"),
    PAYPAL("https://www.paypal.me/"),
    APPLE_PAY("https://www.apple.com/apple-pay/"),
    LINKEDIN("https://www.linkedin.com/in/"),
    INSTAGRAM("https://www.instagram.com/"),
    SNAPCHAT("https://www.snapchat.com/add/"),
    FACEBOOK("https://www.facebook.com/"),
    TIKTOK("https://www.tiktok.com/"),
    VENMO("https://venmo.com/");

    companion object {
        /**
         * Description: This method is used for the purpose of getting share uri string which you can open in browser.
         *
         * @author Ankit Mishra
         * @since 29/11/21
         *
         * @param socialTypeKey Provide social type key
         * @param username Provide username/id of that social.
         */
        fun getShareUrl(socialTypeKey: SocialTypeKey, username: String) =
            socialTypeKey.prefix + username
    }
}

/**
 * Description: This enum class is used for the purpose of differentiating between different phone functionality.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 */
enum class Feature {
    /**
     * Provide Phone number in data when this feature is used.
     */
    PHONE,

    /**
     * Provide Email in data when this feature is used.
     */
    EMAIL,

    /**
     * Provide Phone number and message in data when this feature is used.
     */
    SMS;
}

/**
 * Description: This method is used for the purpose of sending data via different phone features.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param feature Provide phone feature.
 * @param numberOrEmail Provide number or email as per your feature.
 * @param message Provide message. This parameter will only be useful for SMS feature.
 */

fun Context.sendData(
    feature: Feature,
    numberOrEmail: String,
    message: String? = null
) {
    val prefix = when (feature) {
        Feature.PHONE -> "tel:"
        Feature.EMAIL -> "mailto:"
        Feature.SMS -> "sms:"
    }
    val intent = when (feature) {
        Feature.PHONE -> Intent(Intent.ACTION_DIAL)
        else -> Intent(Intent.ACTION_VIEW)
    }
    intent.data = Uri.parse(prefix + numberOrEmail)
    message?.let {
        //This will set the sms body.
        intent.putExtra("sms_body", it)
    }
    startActivity(intent)
}

/**
 * Description: This method is used for the purpose of getting ip address.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @return String if the ip address is found otherwise null.
 */
fun getIPAddressForCellOrWireless(): String? {
    try {
        val en: Enumeration<NetworkInterface> = NetworkInterface.getNetworkInterfaces()
        while (en.hasMoreElements()) {
            val networkInterface: NetworkInterface = en.nextElement()
            val inetAddresses: Enumeration<InetAddress> = networkInterface.inetAddresses
            while (inetAddresses.hasMoreElements()) {
                val inetAddress: InetAddress = inetAddresses.nextElement()
                if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                    return inetAddress.getHostAddress()
                }
            }
        }
    } catch (ex: SocketException) {
        ex.printStackTrace()
    }
    return null
}

/**
 * Description: This method is used for the purpose of getting address from the provided lat and long.
 *
 * @author Ankit Mishra
 * @since 29/11/21
 *
 * @param context Provide context
 * @param latitude Provide latitude
 * @param longitude Provide longitude
 */
fun getCompleteAddressString(
    context: Context,
    latitude: Double?,
    longitude: Double?
): String {
    var strAdd = ""
    if (latitude == null || longitude == null) {
        return strAdd
    }
    val geocoder = Geocoder(context, Locale.getDefault())
    try {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        if (!addresses.isNullOrEmpty()) {
            if (addresses[0].subLocality != null) strAdd =
                strAdd + addresses[0].subLocality + ", "

            if (addresses[0].thoroughfare != null) strAdd =
                strAdd + addresses[0].thoroughfare + ", "

            if (addresses[0].locality != null) strAdd =
                strAdd + addresses[0].locality + ", "

            if (addresses[0].countryName != null) strAdd =
                strAdd + addresses[0].countryName + ", "

            if (addresses[0].postalCode != null) strAdd =
                strAdd + addresses[0].postalCode + " "
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return strAdd
}

/**
 * Showing toast message if it is not empty.
 *
 * @param message Provide message string
 */
fun Context.showMessage(message: String) {
    /**
     * Showing toast if the message is not empty.
     */
    if (message.isNotBlank()) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}

/**
 * Description: This method is used for the purpose of getting request body from json object.
 *
 * @author Ankit Mishra
 * @since 01/12/21
 */
fun JSONObject.getRequestBody() =
    this.toString()
        .toRequestBody(
            AppConstants.CONTENT_TYPE_JSON_FOR_CALL_API.toMediaTypeOrNull()
        )


fun log(text: String) {
    val log = Thread.currentThread().stackTrace.getOrNull(3)
    log?.let {
        Log.d(log.fileName, "${log.methodName}() -> $text")
    }

}


fun Context.getAccessTokenWithPrefix(accessToken: String) =
    getString(R.string.text_access_token_prefix, accessToken)

/**
 * Description: This extension property is used for setting up and getting string text in EditText field.
 *
 * @author Ankit Mishra
 * @since 04/02/22
 */
inline var EditText.textAsString: String
    get() = if (text != null) {
        text.toString()
    } else {
        String()
    }
    set(value) {
        setText(value)
    }

/**
 * Description: This method is used for navigating safely to the NavDirections.
 * Basically this prevents random crashes while navigating.
 *
 * @author Ankit Mishra
 * @since 04/02/22
 */
fun NavController.navigateSafe(destination: NavDirections) = with(this) {
    ifThrowsPrintStackTrace {
        navigate(destination)
    }
//    currentDestination?.getAction(destination.actionId)
//        ?.let { navigate(destination) }
}


/**
 * Description: This extension property is used for checking if the dialog is safe to open or not.
 *
 * @author Ankit Mishra
 * @since 04/02/22
 */
val Activity.canOpenDialog: Boolean
    get() = !isDestroyed && !isFinishing

/**
 * Description: This method is used for getting mime type for uri.
 *
 * @author Ankit Mishra
 * @since 07/02/22
 */
fun Context.getMimeType(uri: Uri): String? {
    return if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        val cr: ContentResolver = contentResolver
        cr.getType(uri)
    } else {
        val fileExtension = MimeTypeMap.getFileExtensionFromUrl(
            uri
                .toString()
        )
        MimeTypeMap.getSingleton().getMimeTypeFromExtension(
            fileExtension.lowercase(Locale.ROOT)
        )
    }
}

/**
 * Description: This extension method is used for getting request body as multipart form from the string.
 *
 * @author Ankit Mishra
 * @since 07/02/22
 */
fun String.toFormData(): RequestBody =
    toRequestBody("multipart/form-data".toMediaTypeOrNull())

/**
  * Description: This extension method is used for getting request body as multipart form from the file.
  *
  * @author Ankit Mishra
  * @since 07/02/22
  */
fun File.toRequestBody(context: Context,defaultMimeType:String=String()): RequestBody =
    asRequestBody((context.getMimeType(Uri.fromFile(this)) ?: defaultMimeType).toMediaTypeOrNull())

inline fun <T> nullIfThrows(func:()->T):T?{
    return try{
        func()
    }catch (e:Exception){
        null
    }
}


/**
 * Description : This function is extension function of View class,
 *               Which can handle the animation with attached view.
 * @author Abhishek Oza
 * @since 16th march 23
 */
fun View.startAnim(animation: Animation, onEnd: () -> Unit={}) {
    animation.setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(p0: Animation?) = Unit

        override fun onAnimationEnd(p0: Animation?) {
            onEnd.invoke()
        }

        override fun onAnimationRepeat(p0: Animation?) = Unit
    })
    this.startAnimation(animation)
}

suspend fun <T> showHideLoader(liveData: MutableLiveData<Event<Resource<T>>>, task:suspend ()->Unit){
    liveData.value=Event(Resource.Loading(true))
    task()
    liveData.value=Event(Resource.Loading(false))
}

inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}

inline fun <reified T : Parcelable> Activity.getIMDataFromIntent(key:String):T{
    return intent.parcelable<T>(key)!!
}
