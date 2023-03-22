package com.oneclick.blackandoneparent.core.network_helpers

import android.content.Context
import com.oneclick.blackandoneparent.core.constants.AppConstants
import com.oneclick.blackandoneparent.core.utils.AppPreference
import com.oneclick.blackandoneparent.R
import com.oneclick.blackandoneparent.core.utils.getAccessTokenWithoutPrefix
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Invocation

@Target(AnnotationTarget.FUNCTION)
annotation class Authenticated

class AuthInterceptor(private val context: Context, private val appPreference: AppPreference) : Interceptor {
    /**
     * Description: This variable will return access token if available else blank string.
     */
    private suspend fun getAccessToken() = context.getString(
        R.string.text_access_token_prefix, appPreference.getAccessTokenWithoutPrefix()
    )

    override fun intercept(chain: Interceptor.Chain): Response {
        val invocation = chain.request()
            .tag(Invocation::class.java)
            ?: return chain.proceed(chain.request())


        val listOfAnnotations=invocation.method()
            .annotations
        for(annotation in listOfAnnotations){
            if(annotation.annotationClass == Authenticated::class){
                println("${annotation.annotationClass} - match true")
            }else{

                println("${annotation.annotationClass} - match false")
            }
        }

        val shouldAttachAuthHeader = invocation.method()
            .annotations
            .any { it.annotationClass == Authenticated::class }


        return if (shouldAttachAuthHeader) {
            val authToken= runBlocking{getAccessToken()}
            chain.proceed(
                chain.request()
                    .newBuilder()
                    .addHeader(AppConstants.API_USER_TOKEN_KEY, authToken)
                    .build()
            )
        }else{
            chain.proceed(chain.request())
        }
    }

}